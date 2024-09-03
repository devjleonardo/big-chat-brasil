import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from '@mui/material';
import { NavbarContainer, NavbarTitle, LogoutButton } from '../../styles/layout/NavbarStyles';
import { useUser } from '../../context/UsuarioContext';

const Navbar: React.FC = () => {
  const { perfil, nomePessoa } = useUser();
  const navigate = useNavigate();
  const [abrir, setAbrir] = useState(false);

  const handleAbrir = () => {
    setAbrir(true);
  };

  const handleClose = () => {
    setAbrir(false);
  };

  const handleSair = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('usuarioId');
    localStorage.removeItem('perfil');
    navigate('/login');
    handleClose();
  };

  return (
    <NavbarContainer>
      <NavbarTitle>
        {perfil === 'CLIENTE' ? 'Painel do Cliente' : 'Painel do Financeiro'} - {nomePessoa}
      </NavbarTitle>
      <LogoutButton onClick={handleAbrir}>Sair</LogoutButton>
      <Dialog
        open={abrir}
        onClose={handleClose}
      >
        <DialogTitle>{"Confirmação"}</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Tem certeza que deseja sair?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancelar
          </Button>
          <Button onClick={handleSair} color="primary" autoFocus>
            Sair
          </Button>
        </DialogActions>
      </Dialog>
    </NavbarContainer>
  );
};

export default Navbar;
