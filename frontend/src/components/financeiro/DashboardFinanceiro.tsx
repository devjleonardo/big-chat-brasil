import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
  DashboardContainer,
  DashboardTitle,
  InfoCard,
  InfoLabel,
  InfoValue,
} from '../../styles/financeiro/DashboardFinanceiroStyles';

const DashboardFinanceiro: React.FC = () => {
  const [resumoFinanceiro, setResumoFinanceiro] = useState<any>(null);

  useEffect(() => {
    axios.get('http://localhost:8080/api/v1/financeiro/resumo')
      .then(response => {
        setResumoFinanceiro(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar resumo financeiro:', error);
      });
  }, []);

  if (!resumoFinanceiro) {
    return <div>Carregando...</div>;
  }

  return (
    <DashboardContainer>
      <DashboardTitle>Dashboard Financeiro</DashboardTitle>

      <InfoCard>
        <InfoLabel>Total de Clientes:</InfoLabel>
        <InfoValue>{resumoFinanceiro.totalClientes}</InfoValue>
      </InfoCard>

      <InfoCard>
        <InfoLabel>Total de Mensagens Enviadas:</InfoLabel>
        <InfoValue>{resumoFinanceiro.totalMensagensEnviadas}</InfoValue>
      </InfoCard>

      <InfoCard>
        <InfoLabel>Total de Receitas:</InfoLabel>
        <InfoValue>R$ {resumoFinanceiro.totalReceitas?.toFixed(2)}</InfoValue>
      </InfoCard>
    </DashboardContainer>
  );
};

export default DashboardFinanceiro;
