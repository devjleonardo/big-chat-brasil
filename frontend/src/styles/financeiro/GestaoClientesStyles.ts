import styled from 'styled-components';

export const GestaoClientesContainer = styled.div`
  margin-left: 250px;
  padding: 20px;
  background-color: #f4f6f8;
  min-height: 100vh;
  margin-top: 60px;
`;

export const GestaoClientesTitle = styled.h1`
  font-size: 24px;
  margin-bottom: 20px;
`;

export const ClientesTable = styled.table`
  width: 100%;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  border-spacing: 0;
`;

export const TableHeader = styled.th`
  padding: 15px;
  background-color: #343a40;
  color: #fff;
  text-align: left;
`;

export const TableRow = styled.tr`
  &:nth-child(even) {
    background-color: #f2f2f2;
  }
`;

export const TableData = styled.td`
  padding: 15px;
  text-align: left;
`;

export const ActionButton = styled.button`
  padding: 8px 12px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 10px;

  &:hover {
    background-color: #0056b3;
  }
`;
