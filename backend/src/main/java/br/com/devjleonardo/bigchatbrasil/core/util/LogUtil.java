package br.com.devjleonardo.bigchatbrasil.core.util;

import org.slf4j.Logger;

public final class LogUtil {

    private static final String MENSAGEM = "{}";
    private static final String MENSAGEM_ERRO = "{}: {}";
    private static final String DETALHES_ERRO = "Detalhes do erro. ";
    private static final String MENSAGEM_COM_ID = "{}: ID {}";

    private LogUtil() {
    }

    public static void registrarExcecao(Logger logger, String mensagemErro, Exception e) {
        logger.error(MENSAGEM_ERRO, mensagemErro, e.getMessage());
        logger.debug(DETALHES_ERRO, e);
    }

    public static void registrarInfo(Logger logger, String mensagem) {
        logger.info(MENSAGEM, mensagem);
    }

    public static void registrarInfoComId(Logger logger, String mensagem, Long id) {
        logger.info(MENSAGEM_COM_ID, mensagem, id);
    }

    public static void registrarAviso(Logger logger, String mensagem) {
        logger.warn(MENSAGEM, mensagem);
    }

}
