import { createGlobalStyle } from 'styled-components';

export const GlobalStyles = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body {
    font-family: Arial, sans-serif;
    background-color: #f4f6f8;
    color: #333;
  }

  form {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }

  input, textarea {
    width: 100%;
    max-width: 100%;
    padding: 10px;
    font-size: 16px;
    border-radius: 4px;
    border: 1px solid #ccc;
  }

  button {
    padding: 10px 20px;
    font-size: 16px;
    color: #fff;
    background-color: #007bff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-top: 10px;

    &:hover {
      background-color: #0056b3;
    }
  }

  h1, h3 {
    color: #333;
    margin-bottom: 20px;
  }

  .info-section {
    margin-bottom: 20px;
    width: 100%;
    display: flex;
    justify-content: space-between;
  }

  .info-row {
    display: flex;
    justify-content: space-between;
    width: 100%;
  }

  .info-item {
    background-color: #f1f1f1;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    flex: 1;
    text-align: center;
    margin: 5px;

    &:first-child {
      margin-left: 0;
    }

    &:last-child {
      margin-right: 0;
    }

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
  }
`;
