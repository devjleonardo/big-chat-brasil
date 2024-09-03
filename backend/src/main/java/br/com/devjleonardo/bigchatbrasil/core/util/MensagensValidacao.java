package br.com.devjleonardo.bigchatbrasil.core.util;

public final class MensagensValidacao {

    public static final String MSG_CAMPO_EXISTENTE = "O %s '%s' já está cadastrado. Por favor, verifique e " +
        "tente novamente";

    private static final String MSG_ENTIDADE_EM_USO = "%s com o ID %d não pode ser removido, pois está em uso";

    public static final String MSG_PLANO_INVALIDO = "Plano inválido: ";

    public static final String MSG_APENAS_PRE_PAGO_PODE_RECEBER_CREDITOS = "Apenas clientes com plano pré-pago " +
        "podem receber créditos.";

    public static final String MSG_ERRO_INCLUIR_CREDITO = "Erro ao incluir crédito para o cliente ID ";

    public static final String MSG_ERRO_INESPERADO_INCLUIR_CREDITO = "Erro inesperado ao incluir crédito. " +
        "Tente novamente.";

    public static final String MSG_APENAS_PRE_PAGO_PODE_CONSULTAR_SALDO = "Apenas clientes com plano pré-pago " +
        "podem consultar saldo.";

    public static final String MSG_APENAS_POS_PAGO_PODE_ALTERAR_LIMITE = "Apenas clientes com plano pós-pago podem " +
        "alterar o limite.";

    public static final String MSG_ERRO_INESPERADO_ALTERAR_LIMITE = "Erro inesperado ao alterar limite. " +
        "Tente novamente.";
    
    private MensagensValidacao() {
    }

    public static String gerarMensagemCampoExistente(String campo, String valor) {
        return String.format(MSG_CAMPO_EXISTENTE, campo, valor);
    }

    public static String gerarMensagemEntidadeEmUso(String nomeEntidade, Long id) {
        return String.format(MSG_ENTIDADE_EM_USO, nomeEntidade, id);
    }

}
