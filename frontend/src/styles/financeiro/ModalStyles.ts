import styled from 'styled-components';
import { Input, Button } from '../cliente/MensagemFormStyles';

export const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
`;

export const ModalContent = styled.div`
  background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
  padding: 30px;
  border-radius: 12px;
  max-width: 800px;
  width: 100%;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  position: relative;
  text-align: center;
`;

export const CloseButton = styled.span`
  position: absolute;
  top: 15px;
  right: 15px;
  font-size: 24px;
  font-weight: bold;
  color: #333;
  cursor: pointer;

  &:hover {
    color: #ff0000;
  }
`;

export const CardFinanceiro = styled.div`
  display: flex;
  justify-content: center;
  margin: 20px 0;
  gap: 20px;
  flex-wrap: wrap;
`;

export const Card = styled.div`
  background-color: white;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  flex: 1 1 30%;
  min-width: 200px;
  max-width: 300px;

  p {
    font-size: 18px;
    margin: 0;
  }
`;

export const InlineFormGroup = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 15px;

  label {
    margin-right: 10px;
    font-weight: bold;
  }

  input, select {
    flex: 1;
    margin-right: 10px;
    width: calc(100% - 170px);
  }

  button {
    margin-left: 10px;
    width: 150px;
  }
`;

export const StyledInput = styled(Input)`

`;

export const StyledSelect = styled.select`
  padding: 10px;
  font-size: 16px;
  border: 2px solid #ccc;
  border-radius: 4px;
  width: calc(100% - 170px);
  box-sizing: border-box;
`;

export const StyledButton = styled(Button)`
  width: 200px;
`;
