import styled from 'styled-components';

export const HistoricoContainer = styled.div`
  margin-left: 250px;
  padding: 20px;
  background-color: #f4f6f8;
  min-height: 100vh;
  margin-top: 60px;
`;

export const HistoricoTitle = styled.h1`
  font-size: 24px;
  margin-bottom: 20px;
`;

export const MessageList = styled.div`
  display: flex;
  flex-direction: column;
`;

export const MessageItem = styled.div`
  background-color: #fff;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

export const MessageDate = styled.div`
  font-size: 16px;
  color: #333;
  font-weight: bold;
  margin-bottom: 5px;
`;

export const MessageRecipient = styled.div`
  font-size: 16px;
  color: #555;
  font-weight: bold;
  margin-bottom: 10px;
`;

export const MessageType = styled.div<{ tipo: string }>`
  font-size: 16px;
  color: ${({ tipo }) => (tipo === 'whatsapp' ? '#25d366' : '#007bff')};
  margin-bottom: 5px;
  font-weight: bold;
`;

export const MessageStatus = styled.div<{ status: string }>`
  font-size: 16px;
  color: ${({ status }) => (status === 'ENVIADA' ? '#28a745' : '#dc3545')};
  margin-bottom: 5px;
  font-weight: bold;
`;

export const MessageCost = styled.div`
  font-size: 16px;
  color: #17a2b8;
  margin-bottom: 5px;
  font-weight: bold;
`;
