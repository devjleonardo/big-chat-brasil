import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
  FormContainer,
  FormTitle,
  FormGroup,
  Label,
  Input,
  TextArea,
  Button,
  InfoSection,
  InfoRow,
  InfoItem,
  LabelCheckbox,
  Checkbox,
} from '../../styles/cliente/MensagemFormNewStyles';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const MensagemForm: React.FC = () => {
  const [numeroDestino, setNumeroDestino] = useState('');
  const [isWhatsapp, setIsWhatsapp] = useState(false);
  const [texto, setTexto] = useState('');
  const [saldo, setSaldo] = useState<number | null>(null);
  const [consumo, setConsumo] = useState<number | null>(null);
  const [limite, setLimite] = useState<number | null>(null);
  const [tipoPlano, setTipoPlano] = useState<string | null>(null);

  useEffect(() => {
    const usuarioId = localStorage.getItem('usuarioId');
    if (!usuarioId) {
      console.error('ID do cliente não encontrado');
      return;
    }

    axios.get(`http://localhost:8080/api/v1/clientes/${usuarioId}`)
      .then(response => {
        const { saldo, consumo, limite, tipoPlano } = response.data;
        setSaldo(saldo);
        setConsumo(consumo);
        setLimite(limite);
        setTipoPlano(tipoPlano);
      })
      .catch(error => {
        console.error('Erro ao buscar dados do cliente:', error);
      });
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const clienteId = localStorage.getItem('usuarioId');

    if (!clienteId) {
      toast.error('ID do cliente não encontrado.');
      return;
    }

    const mensagemRequestDTO = {
      clienteId: Number(clienteId),
      numeroDestino,
      isWhatsapp: isWhatsapp,
      texto,
    };

    try {
      await axios.post('http://localhost:8080/api/v1/sms/enviar', mensagemRequestDTO);
      toast.success('Mensagem enviada com sucesso!');

      const response = await axios.get(`http://localhost:8080/api/v1/clientes/${clienteId}`);
      const { saldo, consumo, limite } = response.data;
      setSaldo(saldo);
      setConsumo(consumo);
      setLimite(limite);

    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        const errorMessage = error.response.data.message || 'Erro ao enviar mensagem.';
        toast.error(errorMessage);
      } else {
        console.error('Erro ao enviar mensagem:', error);
        toast.error('Erro ao enviar mensagem.');
      }
    }
  };

  return (
    <FormContainer onSubmit={handleSubmit}>
      <ToastContainer />
      <FormTitle>Envio de Mensagens</FormTitle>

      <InfoSection>
        {tipoPlano === 'PRE_PAGO' && saldo !== null && (
          <InfoRow>
            <InfoItem className="saldo">
              <Label>Saldo de Créditos:</Label>
              <span>R$ {saldo.toFixed(2)}</span>
            </InfoItem>
          </InfoRow>
        )}

        {tipoPlano === 'POS_PAGO' && consumo !== null && limite !== null && (
          <InfoRow>
            <InfoItem className="consumo">
              <Label>Consumo Atual:</Label>
              <span>R$ {consumo.toFixed(2)}</span>
            </InfoItem>
            <InfoItem className="limite">
              <Label>Limite Disponível:</Label>
              <span>R$ {limite.toFixed(2)}</span>
            </InfoItem>
          </InfoRow>
        )}
      </InfoSection>
      
      <FormGroup>
        <Label htmlFor="numeroDestino">Número de Telefone:</Label>
        <Input
          type="text"
          id="numeroDestino"
          value={numeroDestino}
          onChange={(e) => setNumeroDestino(e.target.value)}
          required
        />
      </FormGroup>

      <FormGroup>
        <LabelCheckbox>
          É WhatsApp?
          <Checkbox
            type="checkbox"
            checked={isWhatsapp}
            onChange={(e) => setIsWhatsapp(e.target.checked)}
          />
        </LabelCheckbox>
      </FormGroup>

      <FormGroup>
        <Label htmlFor="texto">Mensagem:</Label>
        <TextArea
          id="texto"
          value={texto}
          onChange={(e) => setTexto(e.target.value)}
          required
        />
      </FormGroup>

      <Button type="submit">Enviar Mensagem</Button>
    </FormContainer>
  );
};

export default MensagemForm;
