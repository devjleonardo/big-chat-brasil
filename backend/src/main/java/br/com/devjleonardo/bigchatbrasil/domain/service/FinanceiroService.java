package br.com.devjleonardo.bigchatbrasil.domain.service;

import br.com.devjleonardo.bigchatbrasil.core.util.LogUtil;
import br.com.devjleonardo.bigchatbrasil.core.util.MensagensValidacao;
import br.com.devjleonardo.bigchatbrasil.domain.exception.RegraNegocioException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.TipoPlano;
import br.com.devjleonardo.bigchatbrasil.domain.repository.ClienteRepository;
import br.com.devjleonardo.bigchatbrasil.domain.repository.MensagemRepository;
import br.com.devjleonardo.bigchatbrasil.domain.validation.ValidadorFinanceiro;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FinanceiroService {

    private static final Logger logger = LoggerFactory.getLogger(FinanceiroService.class);

    private static final BigDecimal LIMITE_INICIAL_POS_PAGO = BigDecimal.valueOf(200);
    private static final BigDecimal SALDO_INICIAL_PRE_PAGO = BigDecimal.valueOf(50);

    private static final String MSG_CREDITO_INCLUIDO_COM_SUCESSO = "Crédito incluído com sucesso para o cliente ";
    private static final String MSG_LIMITE_ALTERADO_COM_SUCESSO = "Limite alterado com sucesso para o cliente.";

    private final ValidadorFinanceiro validadorFinanceiro;
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;
    private final MensagemRepository mensagemRepository;

    public void processarTransacaoPrePago(Cliente cliente, BigDecimal valor) {
        validadorFinanceiro.validarSaldoSuficiente(cliente, valor);

        subtrairSaldo(cliente, valor);

        LogUtil.registrarInfoComId(logger, "Transação pré-paga processada com sucesso para o cliente",
            cliente.getId());
    }

    public void processarTransacaoPosPago(Cliente cliente, BigDecimal valor) {
        validadorFinanceiro.validarLimiteDeCredito(cliente, valor);

        registrarConsumo(cliente, valor);

        LogUtil.registrarInfoComId(logger, "Transação pós-paga processada com sucesso para o cliente",
            cliente.getId());
    }

    @Transactional
    public String incluirCredito(Long clienteId, BigDecimal valor) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(clienteId);

            if (cliente.getTipoPlano() != TipoPlano.PRE_PAGO) {
                LogUtil.registrarAviso(logger, MensagensValidacao.MSG_APENAS_PRE_PAGO_PODE_RECEBER_CREDITOS);
                throw new RegraNegocioException(MensagensValidacao.MSG_APENAS_PRE_PAGO_PODE_RECEBER_CREDITOS);
            }

            cliente.setSaldo(cliente.getSaldo().add(valor));
            clienteService.salvarCliente(cliente);

            LogUtil.registrarInfoComId(logger, MSG_CREDITO_INCLUIDO_COM_SUCESSO, clienteId);

            return MSG_CREDITO_INCLUIDO_COM_SUCESSO;

        } catch (Exception e) {
            LogUtil.registrarExcecao(logger, MensagensValidacao.MSG_ERRO_INCLUIR_CREDITO + clienteId, e);
            throw new RegraNegocioException(MensagensValidacao.MSG_ERRO_INESPERADO_INCLUIR_CREDITO);
        }
    }

    public BigDecimal consultarSaldo(Long clienteId) {
        Cliente cliente = clienteService.buscarClientePorId(clienteId);

        if (cliente.getTipoPlano() != TipoPlano.PRE_PAGO) {
            LogUtil.registrarAviso(logger, MensagensValidacao.MSG_APENAS_PRE_PAGO_PODE_CONSULTAR_SALDO);
            throw new RegraNegocioException(MensagensValidacao.MSG_APENAS_PRE_PAGO_PODE_CONSULTAR_SALDO);
        }

        LogUtil.registrarInfoComId(logger, "Saldo consultado para o cliente", clienteId);

        return cliente.getSaldo();
    }

    @Transactional
    public String alterarLimite(Long clienteId, BigDecimal novoLimite) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(clienteId);

            if (cliente.getTipoPlano() != TipoPlano.POS_PAGO) {
                LogUtil.registrarAviso(logger, MensagensValidacao.MSG_APENAS_POS_PAGO_PODE_ALTERAR_LIMITE);
                throw new RegraNegocioException(MensagensValidacao.MSG_APENAS_POS_PAGO_PODE_ALTERAR_LIMITE);
            }

            cliente.setLimite(novoLimite);
            clienteService.salvarCliente(cliente);

            LogUtil.registrarInfoComId(logger, MSG_LIMITE_ALTERADO_COM_SUCESSO, clienteId);

            return MSG_LIMITE_ALTERADO_COM_SUCESSO;
        } catch (Exception e) {
            LogUtil.registrarExcecao(logger, MensagensValidacao.MSG_ERRO_INESPERADO_ALTERAR_LIMITE +
                clienteId, e);
            throw new RegraNegocioException(MensagensValidacao.MSG_ERRO_INESPERADO_ALTERAR_LIMITE);
        }
    }

    @Transactional
    public String alterarPlano(Long clienteId, String novoPlano) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(clienteId);
            TipoPlano planoAtualCliente = cliente.getTipoPlano();
            TipoPlano novoPlanoCliente = TipoPlano.valueOf(novoPlano.toUpperCase());

            ajustarPlanoCliente(cliente, planoAtualCliente, novoPlanoCliente);

            cliente.setTipoPlano(novoPlanoCliente);
            clienteService.salvarCliente(cliente);

            LogUtil.registrarInfoComId(logger, "Plano alterado com sucesso para o cliente", clienteId);

            return "Plano alterado com sucesso";
        } catch (IllegalArgumentException e) {
            String mensagemErro = MensagensValidacao.MSG_PLANO_INVALIDO + novoPlano;
            LogUtil.registrarExcecao(logger, mensagemErro, e);
            throw new RegraNegocioException(mensagemErro);
        }
    }

    public Map<String, Object> obterResumoFinanceiro() {
        long totalClientes = clienteRepository.count();
        long totalMensagensEnviadas = clienteRepository.count();
        BigDecimal totalReceitas = mensagemRepository.calcularReceitaTotalEnviadas();

        Map<String, Object> resumo = new HashMap<>();
        resumo.put("totalClientes", totalClientes);
        resumo.put("totalMensagensEnviadas", totalMensagensEnviadas);
        resumo.put("totalReceitas", totalReceitas);

        return resumo;
    }

    private void subtrairSaldo(Cliente cliente, BigDecimal valor) {
        cliente.setSaldo(cliente.getSaldo().subtract(valor));
    }

    private void registrarConsumo(Cliente cliente, BigDecimal valor) {
        cliente.setConsumo(cliente.getConsumo().add(valor));
    }

    private void ajustarPlanoCliente(Cliente cliente, TipoPlano tipoPlanoAtual, TipoPlano novoPlanoCliente) {
        if (tipoPlanoAtual == TipoPlano.PRE_PAGO && novoPlanoCliente == TipoPlano.POS_PAGO) {
            BigDecimal saldoAtual = cliente.getSaldo();
            cliente.setLimite(LIMITE_INICIAL_POS_PAGO.add(saldoAtual));
            cliente.setSaldo(BigDecimal.ZERO);
        } else if (tipoPlanoAtual == TipoPlano.POS_PAGO && novoPlanoCliente == TipoPlano.PRE_PAGO) {
            cliente.setSaldo(SALDO_INICIAL_PRE_PAGO);
            cliente.setLimite(BigDecimal.ZERO);
        }
    }

}
