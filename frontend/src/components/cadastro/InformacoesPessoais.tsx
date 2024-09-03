import { useState, useEffect } from 'react';
import { Input, LargeButton, WarningText, ErrorText, ErrorInput } from '../../styles/cadastro/CadastroClienteStyles';
import { toast, ToastContainer } from 'react-toastify';

const InformacoesPessoais = ({ dadosFormulario, handleChange, proximoPasso, erros, erroGlobal, setErroGlobal }: any) => {
  const [formularioValido, setFormularioValido] = useState(false);

  const renderInput = (
    id: string,
    label: string,
    value: string,
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
    type: string = 'text',
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
            type={type}
            placeholder={label}
            value={value}
            onChange={onChange}
          />
        ) : (
          <Input
            id={id}
            type={type}
            placeholder={label}
            value={value}
            onChange={onChange}
          />
        )}
      </div>
    );
  };

  useEffect(() => {
    const valido =
      dadosFormulario.nome.trim() !== '' &&
      dadosFormulario.email.trim() !== '' &&
      dadosFormulario.senha.trim() !== '' &&
      dadosFormulario.telefone.trim() !== '';
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
      <h2>Informações Pessoais</h2>

      {renderInput(
        'nome',
        'Nome',
        dadosFormulario.nome,
        handleChange('nome'),
        'text',
        erros.find((erro: { nome: string; erro: string }) => erro.nome === 'nome')?.erro
      )}

      {renderInput(
        'email',
        'Email',
        dadosFormulario.email,
        handleChange('email'),
        'email',
        erros.find((erro: { nome: string; erro: string }) => erro.nome === 'email')?.erro
      )}

      {renderInput(
        'senha',
        'Senha',
        dadosFormulario.senha,
        handleChange('senha'),
        'password',
        erros.find((erro: { nome: string; erro: string }) => erro.nome === 'senha')?.erro
      )}

      {renderInput(
        'telefone',
        'Telefone',
        dadosFormulario.telefone,
        handleChange('telefone'),
        'text',
        erros.find((erro: { nome: string; erro: string }) => erro.nome === 'telefone')?.erro
      )}

      {!formularioValido && (
        <WarningText>
          Por favor, preencha todos os campos para continuar.
        </WarningText>
      )}

      <LargeButton
        primary
        onClick={proximoPasso}
        disabled={!formularioValido}
      >
        Próximo
      </LargeButton>
    </div>
  );
};

export default InformacoesPessoais;
