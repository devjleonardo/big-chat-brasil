import React from 'react';
import { SidebarContainer, SidebarLink } from '../../styles/layout/SidebarStyles';
import { Link } from 'react-router-dom';
import { useUser } from '../../context/UsuarioContext';

const Sidebar: React.FC = () => {
  const { perfil } = useUser();

  return (
    <SidebarContainer>
      {perfil && perfil === 'FINANCEIRO' && (
        <>
          <SidebarLink as={Link} to="/financeiro">Financeiro</SidebarLink>
          <SidebarLink as={Link} to="/gestao-clientes">Gestão de Clientes</SidebarLink>
        </>
      )}
      {perfil && perfil === 'CLIENTE' && (
        <>
          <SidebarLink as={Link} to="/dashboard">Dashboard</SidebarLink>
          <SidebarLink as={Link} to="/enviar-mensagem">Enviar Mensagens</SidebarLink>
          <SidebarLink as={Link} to="/historico-envio">Histórico de Envio</SidebarLink>
        </>
      )}
    </SidebarContainer>
  );
};

export default Sidebar;
