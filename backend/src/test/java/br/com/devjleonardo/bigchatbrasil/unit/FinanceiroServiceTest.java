package br.com.devjleonardo.bigchatbrasil.unit;

import br.com.devjleonardo.bigchatbrasil.core.util.MensagensValidacao;
import br.com.devjleonardo.bigchatbrasil.domain.exception.RegraNegocioException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.TipoPlano;
import br.com.devjleonardo.bigchatbrasil.domain.repository.ClienteRepository;
import br.com.devjleonardo.bigchatbrasil.domain.repository.MensagemRepository;
import br.com.devjleonardo.bigchatbrasil.domain.service.ClienteService;
import br.com.devjleonardo.bigchatbrasil.domain.service.FinanceiroService;
import br.com.devjleonardo.bigchatbrasil.domain.validation.ValidadorFinanceiro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FinanceiroServiceTest {

    @InjectMocks
    private FinanceiroService financeiroService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private MensagemRepository mensagemRepository;

    @Mock
    private ValidadorFinanceiro validadorFinanceiro;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNomeEmpresa("Empresa Teste");
        cliente.setCnpj("12345678000199");
        cliente.setTipoPlano(TipoPlano.PRE_PAGO);
        cliente.setSaldo(BigDecimal.valueOf(50));
        cliente.setConsumo(BigDecimal.ZERO);
    }

    @Test
    void processaTransacaoPrePagoComSucesso() {
        BigDecimal valor = BigDecimal.valueOf(20);
        
        doNothing().when(validadorFinanceiro).validarSaldoSuficiente(cliente, valor);
        
        financeiroService.processarTransacaoPrePago(cliente, valor);
        
        assertEquals(BigDecimal.valueOf(30), cliente.getSaldo());
        verify(validadorFinanceiro, times(1)).validarSaldoSuficiente(cliente, valor);
    }

    @Test
    void processaTransacaoPosPagoComSucesso() {
        BigDecimal valor = BigDecimal.valueOf(100);
        cliente.setTipoPlano(TipoPlano.POS_PAGO);
        cliente.setLimite(BigDecimal.valueOf(200));
        
        doNothing().when(validadorFinanceiro).validarLimiteDeCredito(cliente, valor);
        
        financeiroService.processarTransacaoPosPago(cliente, valor);
        
        assertEquals(BigDecimal.valueOf(100), cliente.getConsumo());
        verify(validadorFinanceiro, times(1)).validarLimiteDeCredito(cliente, valor);
    }

    @Test
    void incluiCreditoComSucesso() {
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);
        when(clienteService.salvarCliente(cliente)).thenReturn(cliente);

        String resultado = financeiroService.incluirCredito(1L, BigDecimal.valueOf(30));

        assertEquals("Crédito incluído com sucesso para o cliente ", resultado);
        assertEquals(BigDecimal.valueOf(80), cliente.getSaldo());
        verify(clienteService, times(1)).buscarClientePorId(1L);
        verify(clienteService, times(1)).salvarCliente(cliente);
    }

    @Test
    void consultSaldoComSucesso() {
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);

        BigDecimal saldo = financeiroService.consultarSaldo(1L);

        assertEquals(BigDecimal.valueOf(50), saldo);
        verify(clienteService, times(1)).buscarClientePorId(1L);
    }

    @Test
    void lncaaexcecaoAoConsultarSaldoDeClientePosPago() {
        cliente.setTipoPlano(TipoPlano.POS_PAGO);
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, 
            () -> financeiroService.consultarSaldo(1L));

        assertEquals(MensagensValidacao.MSG_APENAS_PRE_PAGO_PODE_CONSULTAR_SALDO, exception.getMessage());
        verify(clienteService, times(1)).buscarClientePorId(1L);
    }

    @Test
    void alteraLimiteComSucesso() {
        cliente.setTipoPlano(TipoPlano.POS_PAGO);
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);
        when(clienteService.salvarCliente(cliente)).thenReturn(cliente);

        String resultado = financeiroService.alterarLimite(1L, BigDecimal.valueOf(300));

        assertEquals("Limite alterado com sucesso para o cliente.", resultado);
        assertEquals(BigDecimal.valueOf(300), cliente.getLimite());
        verify(clienteService, times(1)).buscarClientePorId(1L);
        verify(clienteService, times(1)).salvarCliente(cliente);
    }

    @Test
    void alteraPlanoDePrePagoParaPosPagoComSucesso() {
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);
        when(clienteService.salvarCliente(cliente)).thenReturn(cliente);

        String resultado = financeiroService.alterarPlano(1L, "POS_PAGO");

        assertEquals("Plano alterado com sucesso", resultado);
        assertEquals(TipoPlano.POS_PAGO, cliente.getTipoPlano());
        assertEquals(BigDecimal.ZERO, cliente.getSaldo());
        assertEquals(BigDecimal.valueOf(250), cliente.getLimite());
        verify(clienteService, times(1)).buscarClientePorId(1L);
        verify(clienteService, times(1)).salvarCliente(cliente);
    }

    @Test
    void lancaExcecaoAoAlterarPlanoComValorInvalido() {
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, 
            () -> financeiroService.alterarPlano(1L, "INVALIDO"));

        assertEquals(MensagensValidacao.MSG_PLANO_INVALIDO + "INVALIDO", exception.getMessage());
        verify(clienteService, times(1)).buscarClientePorId(1L);
        verify(clienteService, never()).salvarCliente(cliente);
    }

}
