import { useState, useEffect, useCallback } from 'react';
import { SelectWrapper, Select, LargeButton, InfoText, IconWrapper } from '../../styles/cadastro/CadastroClienteStyles';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { MdArrowDropDown } from 'react-icons/md';

const ConfiguracoesPlano = ({ dadosFormulario, handleChange, passoAnterior, navegarParaEtapa, setCampoComErros, setErroGlobal }: any) => {
  const navigate = useNavigate();
  const [campoComErros, setErrosInternos] = useState<{ nome: string; erro: string }[]>([]);

  const removeMascara = (valor: string) => {
    return valor.replace(/\D/g, '');
  };

  const submitForm = async () => {
    try {
        const cpfSemMascara = removeMascara(dadosFormulario.cpfResponsavel);
        const cnpjSemMascara = removeMascara(dadosFormulario.cnpj);

        await axios.post('http://localhost:8080/api/v1/clientes', {
            nome: dadosFormulario.nome,
            email: dadosFormulario.email,
            senha: dadosFormulario.senha,
            perfil: "CLIENTE",
            telefone: dadosFormulario.telefone,
            cpfResponsavel: cpfSemMascara,
            cnpj: cnpjSemMascara,
            nomeEmpresa: dadosFormulario.nomeEmpresa,
            tipoPlano: dadosFormulario.tipoPlano,
        });

        toast.success('Cadastro realizado com sucesso!', {
            position: "top-right",
            autoClose: 3000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
        });

        setTimeout(() => {
            navigate('/dashboard');
        }, 3000);
    } catch (error) {
        let errorMessage = 'Erro ao cadastrar cliente. Tente novamente.';
        setCampoComErros([]);
        setErrosInternos([]);

        if (axios.isAxiosError(error) && error.response) {
            if (error.response.data && error.response.data.campoComErros) {
                const erros = error.response.data.campoComErros;
                setCampoComErros(erros);
                setErrosInternos(erros);
            } else if (error.response.data && error.response.data.message) {
                errorMessage = error.response.data.message;
            } else if (error.response.data && error.response.data.detalhe) {
                errorMessage = error.response.data.detalhe;
            }
        }

        if (campoComErros.length === 0) {
            toast.error(errorMessage, {
                position: "top-right",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
            });
        }
    }
  };

  const redirecionarParaEtapaCorreta = useCallback((erros: { nome: string; erro: string }[]) => {
    const primeiroErro = erros[0];
    if (primeiroErro.nome.includes('nome') || primeiroErro.nome.includes('email') || primeiroErro.nome.includes('senha') || primeiroErro.nome.includes('telefone')) {
      navegarParaEtapa(0);
    } else if (primeiroErro.nome.includes('cpfResponsavel') || primeiroErro.nome.includes('cnpj') || primeiroErro.nome.includes('nomeEmpresa')) {
      navegarParaEtapa(1);
    } else if (primeiroErro.nome.includes('tipoPlano')) {
      navegarParaEtapa(2);
    }
  }, [navegarParaEtapa]);
  
  useEffect(() => {
    if (!dadosFormulario.tipoPlano) {
      handleChange('tipoPlano')({ target: { value: 'PRE_PAGO' } });
    }
  }, [dadosFormulario.tipoPlano, handleChange]);

  useEffect(() => {
    if (campoComErros.length > 0) {
      setErroGlobal('Erro no cadastro, revise os campos');
      redirecionarParaEtapaCorreta(campoComErros);
    }
  }, [campoComErros, redirecionarParaEtapaCorreta, setErroGlobal]);

  return (
    <div>
      <ToastContainer />
      <h2>Configurações de Plano</h2>
      <SelectWrapper>
        <Select
          value={dadosFormulario.tipoPlano}
          onChange={(e) => handleChange('tipoPlano')(e)}
          required
        >
          <option value="PRE_PAGO">Pré-pago</option>
          <option value="POS_PAGO">Pós-pago</option>
        </Select>
        <IconWrapper>
          <MdArrowDropDown />
        </IconWrapper>
      </SelectWrapper>
      {dadosFormulario.tipoPlano === 'PRE_PAGO' ? (
        <InfoText>O plano Pré-pago inicia com um saldo de R$50,00</InfoText>
      ) : (
        <InfoText>O plano Pós-pago inicia com um limite de R$200,00</InfoText>
      )}

      <div>
        <LargeButton onClick={passoAnterior}>Voltar</LargeButton>
        <LargeButton
          onClick={submitForm}
          style={{ backgroundColor: '#28a745', color: '#fff', border: '1px solid #28a745' }}
        >
          Cadastrar
        </LargeButton>
      </div>
    </div>
  );
};

export default ConfiguracoesPlano;
