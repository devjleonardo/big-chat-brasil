import React from 'react';
import Sidebar from './components/layout/Sidebar';
import Navbar from './components/layout/Navbar';
import { BrowserRouter as Router, Route, Routes, Navigate, useNavigate } from 'react-router-dom';
import DashboardCliente from './components/cliente/DashboardCliente';
import DashboardFinanceiro from './components/financeiro/DashboardFinanceiro';
import GestaoClientes from './components/financeiro/GestaoClientes';
import { UsuarioProvider, useUser } from './context/UsuarioContext';
import EditarCliente from './components/financeiro/EditarCliente';
import { GlobalStyles } from './styles/GlobalStyles';
import CadastroCliente from './components/cadastro/CadastroCliente';
import MensagemForm from './components/cliente/MensagemForm';
import HistoricoEnvio from './components/cliente/HistoricoEnvio';
import Login from './components/login/Login';

const PrivateRoutes: React.FC = () => {
  const { perfil } = useUser();
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  React.useEffect(() => {
    if (!token) {
      navigate('/login');
    }
  }, [token, navigate]);

  if (!perfil) {
    return <div>Carregando...</div>;
  }

  return (
    <>
      <Sidebar />
      <Navbar />
      <Routes>
        {perfil === 'FINANCEIRO' && (
          <>
            <Route path="/financeiro" element={<DashboardFinanceiro />} />
            <Route path="/gestao-clientes" element={<GestaoClientes />} />
            <Route path="/editar-cliente/:id" element={<EditarCliente />} />
          </>
        )}
        {perfil === 'CLIENTE' && (
          <>
            <Route path="/dashboard" element={<DashboardCliente />} />
            <Route path="/enviar-mensagem" element={<MensagemForm />} />
            <Route path="/historico-envio" element={<HistoricoEnvio />} />
          </>
        )}
        <Route path="/" element={<Navigate to={perfil === 'FINANCEIRO' ? "/financeiro" : "/dashboard"} />} />
      </Routes>
    </>
  );
};

const App: React.FC = () => {
  return (
    <UsuarioProvider>
      <GlobalStyles />
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/cadastro" element={<CadastroCliente />} />
          <Route path="/*" element={<PrivateRoutes />} />
        </Routes>
      </Router>
    </UsuarioProvider>
  );
};

export default App;
