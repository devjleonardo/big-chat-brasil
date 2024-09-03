import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useUser } from '../../context/UsuarioContext';

import {
  LoginContainer,
  LoginForm,
  Input,
  Button,
  SignupLink
} from '../../styles/login/LoginStyles'; 

const Login: React.FC = () => {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const navigate = useNavigate();
  const { setPerfil, setNomePessoa } = useUser();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/v1/autenticacao/signin', {
        email,
        senha,
      });

      if (response.data.token && response.data.perfil && response.data.nomePessoa) {
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('usuarioId', response.data.usuarioId);
        localStorage.setItem('perfil', response.data.perfil);
        localStorage.setItem('nomePessoa', response.data.nomePessoa);
        
        setPerfil(response.data.perfil);
        setNomePessoa(response.data.nomePessoa);
        
        toast.success('Login realizado com sucesso!', {
          position: "top-right",
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });

        navigate('/dashboard');
      } else {
        throw new Error('Token, perfil ou nome do usuário não encontrado na resposta do servidor');
      }

    } catch (error) {
      console.error('Erro ao fazer login:', error);

      toast.error('Erro ao fazer login. Verifique suas credenciais.', {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    }
  };

  const handleSignupClick = () => {
    navigate('/signup');
  };

  return (
    <LoginContainer>
      <ToastContainer />
      <LoginForm onSubmit={handleSubmit}>
        <h2>Login</h2>
        <Input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <Input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          required
        />
        <Button type="submit">Entrar</Button>
        <SignupLink>
          Não tem uma conta? <a onClick={handleSignupClick}>Cadastre-se</a>
        </SignupLink>
      </LoginForm>
    </LoginContainer>
  );
};

export default Login;
