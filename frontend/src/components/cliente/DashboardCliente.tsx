import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
  DashboardContainer,
  DashboardTitle,
  InfoCard,
  InfoLabel,
  InfoValue,
} from '../../styles/cliente/DashboardClienteStyles';

type TipoPlano = 'PRE_PAGO' | 'POS_PAGO';

interface DadosCliente {
  tipoPlano: TipoPlano;
  saldo?: number;
  limite?: number;
  consumo?: number;
}

const DashboardCliente: React.FC = () => {
  const [dadosCliente, setDadosCliente] = useState<DadosCliente | null>(null);
  const [quantidadeMensagens, setQuantidadeMensagens] = useState<number>(0);

  useEffect(() => {
    const usuarioId = localStorage.getItem('usuarioId');
    if (!usuarioId) {
      console.error('ID do cliente não encontrado');
      return;
    }

    axios.get(`http://localhost:8080/api/v1/clientes/${usuarioId}`)
      .then(response => {
        setDadosCliente(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar dados do cliente:', error);
      });

    axios.get(`http://localhost:8080/api/v1/sms/historico/${usuarioId}`)
      .then(response => {
        const mensagens = response.data;
        const mensagensEnviadas = mensagens.filter((msg: any) => msg.status === 'ENVIADA').length;
        setQuantidadeMensagens(mensagensEnviadas);
      })
      .catch(error => {
        console.error('Erro ao buscar histórico de envio:', error);
      });
  }, []);

  if (!dadosCliente) {
    return <div>Carregando...</div>;
  }

  const { tipoPlano, saldo, limite, consumo } = dadosCliente;

  return (
    <DashboardContainer>
      <DashboardTitle>Dashboard do Cliente</DashboardTitle>

      <InfoCard color="#B0BEC5">
        <InfoLabel>Plano:</InfoLabel>
        <InfoValue>{tipoPlano === 'PRE_PAGO' ? 'Pré-pago' : 'Pós-pago'}</InfoValue>
      </InfoCard>

      {tipoPlano === 'PRE_PAGO' && saldo !== undefined && (
        <InfoCard color="#90CAF9">
          <InfoLabel>Saldo de Créditos:</InfoLabel>
          <InfoValue>R$ {saldo.toFixed(2)}</InfoValue>
        </InfoCard>
      )}

      {tipoPlano === 'POS_PAGO' && limite !== undefined && consumo !== undefined && (
        <>
          <InfoCard color="#FFCC80">
            <InfoLabel>Limite:</InfoLabel>
            <InfoValue>R$ {limite.toFixed(2)}</InfoValue>
          </InfoCard>
          <InfoCard color="#A5D6A7">
            <InfoLabel>Consumo:</InfoLabel>
            <InfoValue>R$ {consumo.toFixed(2)}</InfoValue>
          </InfoCard>
        </>
      )}

      <InfoCard color="#FFF59D">
        <InfoLabel>Mensagens Enviadas:</InfoLabel>
        <InfoValue>{quantidadeMensagens}</InfoValue>
      </InfoCard>
    </DashboardContainer>
  );
};

export default DashboardCliente;
