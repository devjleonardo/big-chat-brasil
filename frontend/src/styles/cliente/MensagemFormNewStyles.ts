import styled from 'styled-components';

export const FormContainer = styled.form`
  margin-left: 250px;
  padding: 20px;
  background-color: #f4f6f8;
  min-height: 100vh;
  margin-top: 60px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

export const FormTitle = styled.h1`
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
`;

export const FormGroup = styled.div`
  margin-bottom: 15px;
  width: 100%;
  display: flex;
  flex-direction: column; /* Mantém os labels em cima dos inputs */
  align-items: flex-start;
`;

export const Label = styled.label`
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
`;

export const Input = styled.input`
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
  margin-bottom: 15px; /* Espaço entre o input e o próximo grupo */
`;

export const TextArea = styled.textarea`
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
  height: 100px;
  resize: vertical;
  margin-bottom: 15px; /* Espaço entre o textarea e o próximo grupo */
`;

export const Button = styled.button`
  padding: 10px 20px;
  font-size: 16px;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;

  &:hover {
    background-color: #0056b3;
  }
`;

export const InfoSection = styled.div`
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

export const InfoRow = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

export const InfoItem = styled.div`
  background-color: #f1f1f1;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  flex: 1;
  text-align: center;
  margin: 5px;

  span {
    display: block;
    font-size: 18px;
    color: #007bff;
    margin-top: 5px;
    font-weight: bold;
  }

  &.saldo {
    background-color: #e0f7fa;
    span {
      color: #00796b;
    }
  }

  &.consumo {
    background-color: #fff3e0;
    span {
      color: #e65100;
    }
  }

  &.limite {
    background-color: #e8f5e9;
    span {
      color: #2e7d32;
    }
  }
`;

export const LabelCheckbox = styled.label`
  font-size: 16px;
  font-weight: bold;
  display: flex;
  align-items: center;
  color: #333;
  white-space: nowrap; /* Evita quebra de linha */
`;

export const Checkbox = styled.input`
  margin-left: 10px; /* Muda a posição do checkbox para a direita */
  margin-right: 0;
`;
