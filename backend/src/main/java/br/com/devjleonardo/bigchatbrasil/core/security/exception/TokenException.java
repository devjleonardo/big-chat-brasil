package br.com.devjleonardo.bigchatbrasil.core.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenException extends RuntimeException {

    public TokenException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
