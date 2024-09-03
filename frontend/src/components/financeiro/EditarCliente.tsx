import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {
  FormContainer,
  FormTitle,
  FormGroup,
  Label,
  Input,
  Button,
  ButtonFinanceiro,
  ButtonGroup,
  ButtonGreen
} from '../../styles/cliente/MensagemFormStyles'; 
import {
    ModalOverlay,
    ModalContent,
    CloseButton,
    CardFinanceiro,
    Card,
    InlineFormGroup,
    StyledInput,
    StyledSelect,
    StyledButton
  } from '../../styles/financeiro/ModalStyles';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useParams } from 'react-router-dom';

interface Cliente {
    nomeEmpresa: string;
    cpfResponsavel: string;
    cnpj: string;
    tipoPlano: string;
    saldo: number | null;
    limite: number | null;
    consumo: number | null;
    nomePessoa: string;
    telefonePessoa: string;
    emailUsuario: string;
    creditos?: number;
    novoLimite?: number;
}

const EditarCliente: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const clienteId = Number(id);
  const navigate = useNavigate(); // Hook para navegação

  const [cliente, setCliente] = useState<Cliente>({
    nomeEmpresa: '',
    cpfResponsavel: '',
    cnpj: '',
    tipoPlano: '',
    saldo: null,
    limite: null,
    consumo: null,
    nomePessoa: '',
    telefonePessoa: '',
    emailUsuario: '',
  });

  const [tipoPlanoVisualizado, setTipoPlanoVisualizado] = useState(cliente.tipoPlano);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    axios.get(`http://localhost:8080/api/v1/clientes/${clienteId}`)
      .then(response => {
        const {
          nomeEmpresa,
          cpfResponsavel,
          cnpj,
          tipoPlano,
          saldo,
          limite,
          consumo,
          nomePessoa,
          telefonePessoa,
          emailUsuario
        } = response.data;

        setCliente({
          nomeEmpresa,
          cpfResponsavel,
          cnpj,
          tipoPlano,
          saldo,
          limite,
          consumo,
          nomePessoa,
          telefonePessoa,
          emailUsuario,
        });

        setTipoPlanoVisualizado(tipoPlano);
      })
      .catch(error => {
        console.error('Erro ao buscar cliente:', error);
      });
  }, [clienteId]);

  const handleUpdateCliente = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await axios.put(`http://localhost:8080/api/v1/clientes/${clienteId}`, cliente);
      toast.success('Cliente atualizado com sucesso!');
    } catch (error) {
      if (axios.isAxiosError(error) && error.response) {
        const errorMessage = error.response.data.message || 'Erro ao atualizar cliente.';
        toast.error(errorMessage);
      } else {
        console.error('Erro ao atualizar cliente:', error);
        toast.error('Erro ao atualizar cliente.');
      }
    }
  };

  const handleAdicionarCreditos = async () => {
    try {
      await axios.post(`http://localhost:8080/api/v1/financeiro/incluir-creditos`, { clienteId, valor: cliente.creditos });
      toast.success('Créditos adicionados com sucesso!');
      const updatedSaldo = (cliente.saldo || 0) + (cliente.creditos || 0);
      setCliente({ ...cliente, saldo: updatedSaldo, creditos: undefined });
    } catch (error) {
      console.error('Erro ao adicionar créditos:', error);
      toast.error('Erro ao adicionar créditos.');
    }
  };

  const handleAlterarLimite = async () => {
    try {
      await axios.post(`http://localhost:8080/api/v1/financeiro/alterar-limite`, { clienteId, valor: cliente.novoLimite });
      toast.success('Limite alterado com sucesso!');
    } catch (error) {
      console.error('Erro ao alterar limite:', error);
      toast.error('Erro ao alterar limite.');
    }
  };

  const handleAlterarPlano = async () => {
    try {
      await axios.put(`http://localhost:8080/api/v1/financeiro/alterar-plano`, { clienteId, novoPlano: cliente.tipoPlano });
      toast.success('Plano alterado com sucesso!');

      const response = await axios.get(`http://localhost:8080/api/v1/clientes/${clienteId}`);
      const {
        nomeEmpresa,
        cpfResponsavel,
        cnpj,
        tipoPlano,
        saldo,
        limite,
        consumo,
        nomePessoa,
        telefonePessoa,
        emailUsuario
      } = response.data;

      setCliente({
        nomeEmpresa,
        cpfResponsavel,
        cnpj,
        tipoPlano,
        saldo,
        limite,
        consumo,
        nomePessoa,
        telefonePessoa,
        emailUsuario,
      });

      setTipoPlanoVisualizado(tipoPlano);

    } catch (error) {
      console.error('Erro ao alterar plano:', error);
      toast.error('Erro ao alterar plano.');
    }
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const formatarPlano = (plano: string) => {
    if (plano === 'POS_PAGO') {
      return 'Pós-pago';
    } else if (plano === 'PRE_PAGO') {
      return 'Pré-pago';
    }
    return plano;
  };

  return (
    <FormContainer onSubmit={handleUpdateCliente}>
      <ToastContainer />
      <FormTitle>Editar Cliente</FormTitle>

      <ButtonGroup>
        <Button
          type="button"
          style={{
            backgroundColor: '#fff',
            color: '#007bff',
            border: '1px solid #007bff',
          }}
          onClick={() => navigate(-1)}
        >
          Voltar
        </Button>
        <ButtonFinanceiro type="button" onClick={openModal}>Ver Dados Financeiros</ButtonFinanceiro>
      </ButtonGroup>

      <h3>Informações Pessoais</h3>
      <FormGroup>
        <Label htmlFor="nomePessoa">Nome</Label>
        <Input
          type="text"
          id="nomePessoa"
          value={cliente.nomePessoa}
          onChange={(e) => setCliente({ ...cliente, nomePessoa: e.target.value })}
          required
        />
      </FormGroup>

      <FormGroup>
        <Label htmlFor="emailUsuario">Email</Label>
        <Input
          type="email"
          id="emailUsuario"
          value={cliente.emailUsuario}
          onChange={(e) => setCliente({ ...cliente, emailUsuario: e.target.value })}
          required
        />
      </FormGroup>

      <FormGroup>
        <Label htmlFor="telefonePessoa">Telefone</Label>
        <Input
          type="text"
          id="telefonePessoa"
          value={cliente.telefonePessoa}
          onChange={(e) => setCliente({ ...cliente, telefonePessoa: e.target.value })}
          required
        />
      </FormGroup>

      <h3>Dados da Empresa</h3>
      <FormGroup>
        <Label htmlFor="nomeEmpresa">Nome da Empresa</Label>
        <Input
          type="text"
          id="nomeEmpresa"
          value={cliente.nomeEmpresa}
          onChange={(e) => setCliente({ ...cliente, nomeEmpresa: e.target.value })}
          required
        />
      </FormGroup>

      <FormGroup>
        <Label htmlFor="cpfResponsavel">CPF do Responsável</Label>
        <Input
          type="text"
          id="cpfResponsavel"
          value={cliente.cpfResponsavel}
          onChange={(e) => setCliente({ ...cliente, cpfResponsavel: e.target.value })}
          required
        />
      </FormGroup>

      <FormGroup>
        <Label htmlFor="cnpj">CNPJ</Label>
        <Input
          type="text"
          id="cnpj"
          value={cliente.cnpj}
          onChange={(e) => setCliente({ ...cliente, cnpj: e.target.value })}
          required
        />
      </FormGroup>

      <ButtonGreen type="submit">Salvar Alterações</ButtonGreen>

      {isModalOpen && (
        <ModalOverlay onClick={closeModal}>
          <ModalContent onClick={(e) => e.stopPropagation()}>
            <CloseButton onClick={closeModal}>&times;</CloseButton>
            <h2>Dados Financeiros do Cliente</h2>
            <CardFinanceiro>
              {tipoPlanoVisualizado === 'PRE_PAGO' && (
                <>
                  <Card>
                    <p><strong>Saldo de Créditos:</strong> R$ {cliente.saldo?.toFixed(2)}</p>
                  </Card>
                  <Card>
                    <p><strong>Plano Atual:</strong> {formatarPlano(tipoPlanoVisualizado)}</p>
                  </Card>
                </>
              )}
              {tipoPlanoVisualizado === 'POS_PAGO' && (
                <>
                  <Card>
                    <p><strong>Limite Disponível:</strong> R$ {cliente.limite?.toFixed(2)}</p>
                  </Card>
                  <Card>
                    <p><strong>Consumo:</strong> {cliente.consumo !== null ? `R$ ${cliente.consumo.toFixed(2)}` : 'Não disponível'}</p>
                  </Card>
                  <Card>
                    <p><strong>Plano Atual:</strong> {formatarPlano(tipoPlanoVisualizado)}</p>
                  </Card>
                </>
              )}
            </CardFinanceiro>

            {cliente.tipoPlano === 'PRE_PAGO' && (
              <InlineFormGroup>
                <Label htmlFor="creditos">Adicionar Créditos</Label>
                <StyledInput
                  type="number"
                  id="creditos"
                  value={cliente.creditos || ''}
                  onChange={(e) => setCliente({ ...cliente, creditos: Number(e.target.value) })}
                />
                <StyledButton type="button" onClick={handleAdicionarCreditos}>Adicionar Créditos</StyledButton>
              </InlineFormGroup>
            )}

            {cliente.tipoPlano === 'POS_PAGO' && (
              <InlineFormGroup>
                <Label htmlFor="novoLimite">Alterar Limite</Label>
                <StyledInput
                  type="number"
                  id="novoLimite"
                  value={cliente.novoLimite || ''}
                  onChange={(e) => setCliente({ ...cliente, novoLimite: Number(e.target.value) })}
                />
                <StyledButton type="button" onClick={handleAlterarLimite}>Alterar Limite</StyledButton>
              </InlineFormGroup>
            )}

            <InlineFormGroup>
              <Label htmlFor="novoPlano">Alterar Plano</Label>
              <StyledSelect
                id="novoPlano"
                value={cliente.tipoPlano}
                onChange={(e) => setCliente({ ...cliente, tipoPlano: e.target.value })}
              >
                <option value="PRE_PAGO">Pré-pago</option>
                <option value="POS_PAGO">Pós-pago</option>
              </StyledSelect>
              <StyledButton type="button" onClick={handleAlterarPlano}>Alterar Plano</StyledButton>
            </InlineFormGroup>
            <StyledButton type="button" onClick={closeModal}>Fechar</StyledButton>
          </ModalContent>
        </ModalOverlay>
      )}
    </FormContainer>
  );
};

export default EditarCliente;
