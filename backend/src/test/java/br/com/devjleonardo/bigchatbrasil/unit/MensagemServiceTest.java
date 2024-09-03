package br.com.devjleonardo.bigchatbrasil.unit;

import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Mensagem;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.StatusMensagem;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.TipoPlano;
import br.com.devjleonardo.bigchatbrasil.domain.repository.MensagemRepository;
import br.com.devjleonardo.bigchatbrasil.domain.service.FinanceiroService;
import br.com.devjleonardo.bigchatbrasil.domain.service.MensagemService;
import br.com.devjleonardo.bigchatbrasil.domain.service.SimulacaoEnvioSmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MensagemServiceTest {

    @InjectMocks
    private MensagemService mensagemService;

    @Mock
    private MensagemRepository mensagemRepository;

    @Mock
    private FinanceiroService financeiroService;

    @Mock
    private SimulacaoEnvioSmsService simulacaoEnvioSmsService;

    private Mensagem mensagem;
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

        mensagem = new Mensagem();
        mensagem.setId(1L);
        mensagem.setCliente(cliente);
        mensagem.setNumeroDestino("+5511999999999");
        mensagem.setTexto("Mensagem de teste");
    }

    @Test
    void enviaMensagemComSucessoParaClientePrePago() {
        doNothing().when(financeiroService).processarTransacaoPrePago(cliente, BigDecimal.valueOf(0.25));
        doNothing().when(simulacaoEnvioSmsService).enviar(mensagem.getNumeroDestino(), mensagem.getTexto());
        when(mensagemRepository.save(mensagem)).thenReturn(mensagem);

        String resultado = mensagemService.enviarMensagem(mensagem);

        assertEquals("Mensagem enviada com sucesso.", resultado);
        assertEquals(StatusMensagem.ENVIADA, mensagem.getStatus());
        assertEquals(LocalDate.now(), mensagem.getDataEnvio());
        assertEquals(BigDecimal.valueOf(0.25), mensagem.getCusto());

        verify(financeiroService, times(1)).processarTransacaoPrePago(cliente, BigDecimal.valueOf(0.25));
        verify(simulacaoEnvioSmsService, times(1)).enviar(mensagem.getNumeroDestino(), mensagem.getTexto());
        verify(mensagemRepository, times(1)).save(mensagem);
    }

    @Test
    void enviaMensagemComSucessoParaClientePosPago() {
        cliente.setTipoPlano(TipoPlano.POS_PAGO);

        doNothing().when(financeiroService).processarTransacaoPosPago(cliente, BigDecimal.valueOf(0.25));
        doNothing().when(simulacaoEnvioSmsService).enviar(mensagem.getNumeroDestino(), mensagem.getTexto());
        when(mensagemRepository.save(mensagem)).thenReturn(mensagem);

        String resultado = mensagemService.enviarMensagem(mensagem);

        assertEquals("Mensagem enviada com sucesso.", resultado);
        assertEquals(StatusMensagem.ENVIADA, mensagem.getStatus());
        assertEquals(LocalDate.now(), mensagem.getDataEnvio());
        assertEquals(BigDecimal.valueOf(0.25), mensagem.getCusto());

        verify(financeiroService, times(1)).processarTransacaoPosPago(cliente, BigDecimal.valueOf(0.25));
        verify(simulacaoEnvioSmsService, times(1)).enviar(mensagem.getNumeroDestino(), mensagem.getTexto());
        verify(mensagemRepository, times(1)).save(mensagem);
    }

}
