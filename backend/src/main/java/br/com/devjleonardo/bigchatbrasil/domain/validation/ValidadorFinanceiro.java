package br.com.devjleonardo.bigchatbrasil.domain.validation;

import br.com.devjleonardo.bigchatbrasil.domain.exception.LimiteExcedidoException;
import br.com.devjleonardo.bigchatbrasil.domain.exception.SaldoInsuficienteException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidadorFinanceiro {

    private static final String MSG_SALDO_INSUFICIENTE = "Seu saldo é insuficiente para enviar o SMS.";
    private static final String MSG_LIMITE_EXCEDIDO = "Seu limite de crédito foi excedido.";

    public void validarSaldoSuficiente(Cliente cliente, BigDecimal valor) {
        if (cliente.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException(MSG_SALDO_INSUFICIENTE);
        }
    }

    public void validarLimiteDeCredito(Cliente cliente, BigDecimal valor) {
        BigDecimal novoConsumo = cliente.getConsumo().add(valor);
        if (novoConsumo.compareTo(cliente.getLimite()) > 0) {
            throw new LimiteExcedidoException(MSG_LIMITE_EXCEDIDO);
        }
    }

}
