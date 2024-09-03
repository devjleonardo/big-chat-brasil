package br.com.devjleonardo.bigchatbrasil.domain.exception;

public class LimiteExcedidoException extends RuntimeException {

    public LimiteExcedidoException(String message) {
        super(message);
    }

}
