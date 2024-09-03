package br.com.devjleonardo.bigchatbrasil.domain.service;

import br.com.devjleonardo.bigchatbrasil.core.util.LogUtil;
import br.com.devjleonardo.bigchatbrasil.domain.exception.RegraNegocioException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Mensagem;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.StatusMensagem;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.TipoPlano;
import br.com.devjleonardo.bigchatbrasil.domain.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MensagemService {

    private static final Logger logger = LoggerFactory.getLogger(MensagemService.class);

    private static final BigDecimal CUSTO_SMS = BigDecimal.valueOf(0.25);

    private static final String MSG_SUCESSO_ENVIO = "Mensagem enviada com sucesso.";
    private static final String MSG_FALHA_ENVIO = "Falha ao enviar a mensagem: ";

    private final MensagemRepository mensagemRepository;
    private final FinanceiroService financeiroService;
    private final SimulacaoEnvioSmsService simulacaoEnvioSmsService;

    @Transactional
    public String enviarMensagem(Mensagem mensagem) {
        try {
            Cliente cliente = mensagem.getCliente();

            if (cliente.getTipoPlano() == TipoPlano.PRE_PAGO) {
                financeiroService.processarTransacaoPrePago(cliente, CUSTO_SMS);
            } else if (cliente.getTipoPlano() == TipoPlano.POS_PAGO) {
                financeiroService.processarTransacaoPosPago(cliente, CUSTO_SMS);
            }

            simulacaoEnvioSmsService.enviar(mensagem.getNumeroDestino(), mensagem.getTexto());

            mensagem.setStatus(StatusMensagem.ENVIADA);
            mensagem.setDataEnvio(LocalDate.now());
            mensagem.setCusto(CUSTO_SMS);

            mensagemRepository.save(mensagem);

            LogUtil.registrarInfo(logger, MSG_SUCESSO_ENVIO);

            return MSG_SUCESSO_ENVIO;
        } catch (Exception e) {
            mensagem.setStatus(StatusMensagem.FALHA);
            mensagem.setDataEnvio(LocalDate.now());
            mensagemRepository.save(mensagem);

            LogUtil.registrarExcecao(logger, MSG_FALHA_ENVIO, e);

            throw new RegraNegocioException(MSG_FALHA_ENVIO + e.getMessage());
        }
    }

    public List<Mensagem> buscarHistoricoEnvioPorCliente(Long clienteId) {
        return mensagemRepository.buscarHistoricoEnvioPorCliente(clienteId);
    }

}
