import styled from 'styled-components';

export const CadastroContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  width: 100%;
`;

export const CadastroForm = styled.div`
  width: 100%;
  max-width: 500px;
`;

export const Input = styled.input<{ isErrored?: boolean }>`
  padding: 8px;
  font-size: 16px;
  border-radius: 4px;
  border: 2px solid ${props => (props.isErrored ? '#ff4d4f' : '#ccc')};
  width: 100%;
  box-sizing: border-box;
  margin-bottom: 8px;
  &:focus {
    border-color: ${props => (props.isErrored ? '#ff4d4f' : '#4d90fe')};
  }
`;

export const LargeButton = styled.button<{ primary?: boolean; disabled?: boolean }>`
  width: 100%;
  padding: 15px;
  margin-top: 10px;
  background-color: ${(props) => 
    props.disabled 
      ? '#cccccc' 
      : props.primary 
        ? '#007bff' 
        : '#fff'};
  color: ${(props) => 
    props.disabled 
      ? '#666666' 
      : props.primary 
        ? '#fff' 
        : '#000'};
  border: 1px solid ${(props) => 
    props.disabled 
      ? '#cccccc' 
      : props.primary 
        ? '#007bff' 
        : '#ccc'};
  border-radius: 4px;
  cursor: ${(props) => (props.disabled ? 'not-allowed' : 'pointer')};
  font-size: 16px;
  box-sizing: border-box;

  &:hover {
    background-color: ${(props) =>
      props.disabled
        ? '#cccccc'
        : props.primary
          ? '#0056b3'
          : '#e6e6e6'};
  }
`;

export const SelectWrapper = styled.div`
  position: relative;
  width: 100%;
`;

export const Select = styled.select`
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #ccc;
  appearance: none;
  background-color: #fff;
  font-size: 16px;
  padding-right: 30px; /* Espaço para o ícone */
`;

export const IconWrapper = styled.div`
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  pointer-events: none; /* Faz com que o clique passe direto para o select */
  display: flex;
  align-items: center;
  font-size: 24px;
  color: #555;
`;

export const InfoText = styled.p`
  margin-top: 10px;
  font-size: 16px;
  color: #333;
  font-weight: bold;
  background-color: #f0f8ff;
  padding: 10px;
  border-radius: 4px;
`;

export const WarningText = styled.p`
  color: #ff4d4f;
  font-size: 14px;
  margin: 4px 0 0 0;
  padding-left: 8px;
  border-left: 3px solid #ff4d4f;
  background-color: #fff5f5;
`;

export const ErrorText = styled.span`
  color: red;
  font-size: 12px;
  margin-top: 5px;
  display: block;
`;

export const ErrorInput = styled(Input)`
  border-color: red;
`;