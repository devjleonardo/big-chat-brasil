import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  GestaoClientesContainer,
  GestaoClientesTitle,
  ClientesTable,
  TableHeader,
  TableRow,
  TableData,
  ActionButton
} from '../../styles/financeiro/GestaoClientesStyles';

const GestaoClientes: React.FC = () => {
  const navigate = useNavigate();
  const [clientes, setClientes] = useState([]);

  useEffect(() => {
    const fetchClientes = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/clientes');
        setClientes(response.data);
      } catch (error) {
        console.error('Erro ao buscar clientes:', error);
      }
    };

    fetchClientes();
  }, []);

  const handleEdit = (id: number) => {
    navigate(`/editar-cliente/${id}`);
  };

  const formatarTelefone = (telefone: string) => {
    const telefoneLimpo = telefone.replace(/\D/g, '');
    const match = telefoneLimpo.match(/^(\d{2})(\d{5})(\d{4})$/);
    if (match) {
      return `(${match[1]}) ${match[2]}-${match[3]}`;
    }
    return telefone;
  };

  return (
    <GestaoClientesContainer>
      <GestaoClientesTitle>Gestão de Clientes</GestaoClientesTitle>
      <ClientesTable>
        <thead>
          <tr>
            <TableHeader>Nome</TableHeader>
            <TableHeader>Email</TableHeader>
            <TableHeader>Telefone</TableHeader>
            <TableHeader>Empresa</TableHeader>
            <TableHeader>Ações</TableHeader>
          </tr>
        </thead>
        <tbody>
          {clientes.map((cliente: any) => (
            <TableRow key={cliente.id}>
              <TableData>{cliente.nomePessoa}</TableData>
              <TableData>{cliente.emailUsuario}</TableData>
              <TableData>{formatarTelefone(cliente.telefonePessoa)}</TableData>
              <TableData>{cliente.nomeEmpresa}</TableData>

              <TableData>
                <ActionButton onClick={() => handleEdit(cliente.id)}>Editar</ActionButton>
              </TableData>
            </TableRow>
          ))}
        </tbody>
      </ClientesTable>
    </GestaoClientesContainer>
  );
};

export default GestaoClientes;
