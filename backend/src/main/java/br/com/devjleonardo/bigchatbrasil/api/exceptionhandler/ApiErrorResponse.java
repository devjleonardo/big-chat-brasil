package br.com.devjleonardo.bigchatbrasil.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class ApiErrorResponse {

    private Integer status;

    private String titulo;

    private String detalhe;

    private String message;

    private List<CampoComErro> campoComErros;

    public static class ApiErrorResponseBuilder {
        public ApiErrorResponseBuilder message(Object message) {
            this.message = ApiErrorFormatter.extrairMensagemException(message);
            return this;
        }
    }

    @Builder
    @Getter
    public static class CampoComErro {

        private String nome;

        private String erro;

    }

}
