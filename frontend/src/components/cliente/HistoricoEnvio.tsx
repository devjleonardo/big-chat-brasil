import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
  HistoricoContainer,
  HistoricoTitle,
  MessageList,
  MessageItem,
  MessageDate,
  MessageRecipient,
  MessageStatus,
  MessageType,
  MessageCost,
} from '../../styles/cliente/HistoricoEnvioStyles';

const HistoricoEnvio: React.FC = () => {
  const [mensagens, setMensagens] = useState<Array<{
    dataEnvio: string;
    numeroDestino: string;
    status: string;
    custo: number;
    isWhatsapp: boolean;
  }>>([]);

  useEffect(() => {
    const usuarioId = localStorage.getItem('usuarioId');
    if (!usuarioId) {
      console.error('ID do cliente não encontrado');
      return;
    }

    axios.get(`http://localhost:8080/api/v1/sms/historico/${usuarioId}`)
      .then(response => {
        setMensagens(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar histórico de envio:', error);
      });
  }, []);

  const formatarData = (data: string) => {
    const dataObj = new Date(data);
    return dataObj.toLocaleDateString('pt-BR');
  };

  const formatarNumero = (numero: string) => {
    const numeroFormatado = numero.replace(/^(\d{2})(\d{5})(\d{4})$/, "($1) $2-$3");
    return numeroFormatado;
  };

  return (
    <HistoricoContainer>
      <HistoricoTitle>Histórico de Envio</HistoricoTitle>
      <MessageList>
        {mensagens.map((mensagem, index) => (
          <MessageItem key={index}>
            <MessageDate>Data de Envio: {formatarData(mensagem.dataEnvio)}</MessageDate>
            <MessageRecipient>Destinatário: {formatarNumero(mensagem.numeroDestino)}</MessageRecipient>
            <MessageType tipo={mensagem.isWhatsapp ? 'whatsapp' : 'sms'}>
              Tipo: {mensagem.isWhatsapp ? 'WhatsApp' : 'SMS'}
            </MessageType>
            <MessageStatus status={mensagem.status}>Status: {mensagem.status}</MessageStatus>
            <MessageCost>Custo: R$ {mensagem.custo.toFixed(2)}</MessageCost>
          </MessageItem>
        ))}
      </MessageList>
    </HistoricoContainer>
  );
};

export default HistoricoEnvio;
