package br.com.devjleonardo.bigchatbrasil.core.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SenhaIncorretaException extends RuntimeException {

    public SenhaIncorretaException(String mensagem) {
        super(mensagem);
    }

}
