package br.com.devjleonardo.bigchatbrasil.core.security.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "br.com.devjleonardo.bigchatbrasil.core.security")
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Object> handleTokenException(TokenException ex) {
        return new ResponseEntity<>("Token inválido ou expirado", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<Object> handleSenhaIncorretaException(SenhaIncorretaException ex) {
        return new ResponseEntity<>("Senha incorreta", HttpStatus.UNAUTHORIZED);
    }

}
