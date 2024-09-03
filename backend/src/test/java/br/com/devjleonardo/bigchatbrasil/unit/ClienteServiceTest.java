package br.com.devjleonardo.bigchatbrasil.unit;

import br.com.devjleonardo.bigchatbrasil.domain.exception.ClienteNaoEncontradoException;
import br.com.devjleonardo.bigchatbrasil.domain.exception.EntidadeEmUsoException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.TipoPlano;
import br.com.devjleonardo.bigchatbrasil.domain.repository.ClienteRepository;
import br.com.devjleonardo.bigchatbrasil.domain.service.ClienteService;
import br.com.devjleonardo.bigchatbrasil.domain.service.PessoaService;
import br.com.devjleonardo.bigchatbrasil.domain.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private UsuarioService usuarioService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setTelefone("+5511999999999");

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNomeEmpresa("Empresa Teste");
        cliente.setCnpj("12345678000199");
        cliente.setTipoPlano(TipoPlano.PRE_PAGO);
        cliente.setPessoa(pessoa);
        cliente.setSaldo(BigDecimal.valueOf(50));
    }

    @Test
    void listaTodosClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> clientes = clienteService.listarTodosClientes();

        assertNotNull(clientes);
        assertFalse(clientes.isEmpty());
        assertEquals(1, clientes.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void buscaClientePorIdComSucesso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente clienteEncontrado = clienteService.buscarClientePorId(1L);

        assertNotNull(clienteEncontrado);
        assertEquals("Empresa Teste", clienteEncontrado.getNomeEmpresa());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void lancaExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.buscarClientePorId(2L));
        verify(clienteRepository, times(1)).findById(2L);
    }

    @Test
    void salvaClienteComSucesso() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteSalvo = clienteService.salvarCliente(cliente);

        assertNotNull(clienteSalvo);
        assertEquals(BigDecimal.valueOf(50), clienteSalvo.getSaldo());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void inicializaValoresFinanceirosAoSalvarClienteNovo() {
        cliente.setId(null);
        cliente.setTipoPlano(TipoPlano.PRE_PAGO);

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteSalvo = clienteService.salvarClienteComUsuario(cliente, "email@teste.com",
            "senha123", "CLIENTE");

        assertEquals(BigDecimal.valueOf(50), clienteSalvo.getSaldo());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void removeClienteComSucesso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).delete(cliente);

        clienteService.removerClientePorId(1L);

        verify(clienteRepository, times(1)).delete(cliente);
        verify(clienteRepository, times(1)).flush();
    }

    @Test
    void lancaExcecaoQuandoRemoverClienteEmUso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doThrow(DataIntegrityViolationException.class).when(clienteRepository).delete(cliente);

        assertThrows(EntidadeEmUsoException.class, () -> clienteService.removerClientePorId(1L));

        verify(clienteRepository, times(1)).delete(cliente);
    }

}
