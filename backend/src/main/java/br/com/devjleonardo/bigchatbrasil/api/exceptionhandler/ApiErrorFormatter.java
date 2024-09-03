package br.com.devjleonardo.bigchatbrasil.api.exceptionhandler;

public final class ApiErrorFormatter {

    private ApiErrorFormatter() {
    }

    public static String extrairMensagemException(Object message) {
        if (message instanceof ApiErrorMessages mensagemTitulo) {
            return mensagemTitulo.getMensagem();
        } else if (message instanceof String mensagemDetalhe) {
            return mensagemDetalhe;
        }

        return "";
    }

    public static String stringFormat(Object format, Object... args) {
        String mensagemFormatada = "";

        if (format instanceof ApiErrorMessages apiErrorMessages) {
            mensagemFormatada = apiErrorMessages.getMensagem();
        } else if (format instanceof String string) {
            mensagemFormatada = string;
        }

        return String.format(mensagemFormatada, args);
    }

}
