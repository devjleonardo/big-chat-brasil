import React, { useState } from 'react';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import InformacoesPessoais from './InformacoesPessoais';
import DadosEmpresa from './DadosEmpresa';
import ConfiguracoesPlano from './ConfiguracoesPlano';
import { CadastroContainer, CadastroForm} from '../../styles/cadastro/CadastroClienteStyles';

const steps = [
  'Informações Pessoais',
  'Dados da Empresa',
  'Configurações de Plano'
];

const CadastroCliente: React.FC = () => {
  const [step, setStep] = useState(0);
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    senha: '',
    telefone: '',
    nomeEmpresa: '',
    cnpj: '',
    cpfResponsavel: '',
    tipoPlano: '',
  });
  
  const [campoComErros, setCampoComErros] = useState<{ nome: string; erro: string }[]>([]);
  const [erroGlobal, setErroGlobal] = useState<string | null>(null);

  const handleChange = (input: string) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [input]: e.target.value });
  };

  const proximoPasso = () => {
    setStep(step + 1);
  };

  const passoAnterior = () => {
    setStep(step - 1);
  };

  const navegarParaEtapa = (stepNumber: number) => {
    setStep(stepNumber);
  };

  return (
    <CadastroContainer>
      <Stepper activeStep={step}>
        {steps.map((label, index) => (
          <Step key={index}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
      <CadastroForm>
        {step === 0 && (
          <InformacoesPessoais
            dadosFormulario={formData}
            handleChange={handleChange}
            proximoPasso={proximoPasso}
            erros={campoComErros.filter(erro => erro.nome.includes('nome') || erro.nome.includes('email') || erro.nome.includes('senha') || erro.nome.includes('telefone'))}
            erroGlobal={erroGlobal}
            setErroGlobal={setErroGlobal}
          />
        )}
        {step === 1 && (
          <DadosEmpresa
            dadosFormulario={formData}
            handleChange={handleChange}
            proximoPasso={proximoPasso}
            passoAnterior={passoAnterior}
            erros={campoComErros.filter(erro => erro.nome.includes('cpfResponsavel') || erro.nome.includes('cnpj') || erro.nome.includes('nomeEmpresa'))}
            erroGlobal={erroGlobal}
            setErroGlobal={setErroGlobal}
          />
        )}
        {step === 2 && (
          <ConfiguracoesPlano
            dadosFormulario={formData}
            handleChange={handleChange}
            passoAnterior={passoAnterior}
            navegarParaEtapa={navegarParaEtapa}
            setCampoComErros={setCampoComErros}
            erroGlobal={erroGlobal}
            setErroGlobal={setErroGlobal}
          />
        )}
      </CadastroForm>
    </CadastroContainer>
  );
};

export default CadastroCliente;
