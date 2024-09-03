import { useState, useEffect } from 'react';
import { Input, LargeButton, ErrorText, ErrorInput } from '../../styles/cadastro/CadastroClienteStyles';
import { toast, ToastContainer } from 'react-toastify';

const DadosEmpresa = ({ dadosFormulario, handleChange, proximoPasso, passoAnterior, erros, erroGlobal, setErroGlobal }: any) => {
  const [formularioValido, setFormularioValido] = useState(false);

  const handleCpfChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value.replace(/\D/g, '');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    handleChange('cpfResponsavel')({ target: { value } });
  };

  const handleCnpjChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value.replace(/\D/g, '');
    value = value.replace(/(\d{2})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d)/, '$1/$2');
    value = value.replace(/(\d{4})(\d{1,2})$/, '$1-$2');
    handleChange('cnpj')({ target: { value } });
  };

  const renderInput = (
    id: string,
    label: string,
    value: string,
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
    maxLength: number,
    errorMsg?: string
  ) => {
    const isError = !!errorMsg;

    return (
      <div>
        <label htmlFor={id}>{label}</label>
        {isError && <ErrorText>{errorMsg}</ErrorText>}
        {isError ? (
          <ErrorInput
            id={id}
            type="text"
            value={value}
            onChange={onChange}
            maxLength={maxLength}
          />
        ) : (
          <Input
            id={id}
            type="text"
            value={value}
            onChange={onChange}
            maxLength={maxLength}
          />
        )}
      </div>
    );
  };

  useEffect(() => {
    const valido =
      dadosFormulario.cpfResponsavel.length === 14 &&
      dadosFormulario.cnpj.length === 18 &&
      dadosFormulario.nomeEmpresa.trim() !== '';
    setFormularioValido(valido);
  }, [dadosFormulario]);

  useEffect(() => {
    if (erroGlobal) {
      toast.error(erroGlobal, {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      setErroGlobal(null);
    }
  }, [erroGlobal, setErroGlobal]);
  
  return (
    <div>
      <ToastContainer />

      <h2>Dados da Empresa</h2>

      {renderInput(
        'cpfResponsavel',
        'CPF do Responsável',
        dadosFormulario.cpfResponsavel,
        handleCpfChange,
        14,
        erros.find((erro: { nome: string; erro: string }) => erro.nome === 'cpfResponsavel')?.erro
      )}

      {renderInput(
        'cnpj',
        'CNPJ',
        dadosFormulario.cnpj,
        handleCnpjChange,
        18,
        erros.find((erro: { nome: string; erro: string }) => erro.nome === 'cnpj')?.erro
      )}

      {renderInput(
        'nomeEmpresa',
        'Nome da Empresa',
        dadosFormulario.nomeEmpresa,
        handleChange('nomeEmpresa'),
        100,
        erros.find((erro: { nome: string; erro: string }) => erro.nome === 'nomeEmpresa')?.erro
      )}

      {!formularioValido && (
        <ErrorText>
          Por favor, preencha todos os campos corretamente para continuar.
        </ErrorText>
      )}

      <div>
        <LargeButton onClick={passoAnterior}>Voltar</LargeButton>
        <LargeButton primary onClick={proximoPasso} disabled={!formularioValido}>
          Próximo
        </LargeButton>
      </div>
    </div>
  );
};

export default DadosEmpresa;
