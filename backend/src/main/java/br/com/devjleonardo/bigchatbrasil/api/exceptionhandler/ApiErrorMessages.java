package br.com.devjleonardo.bigchatbrasil.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorMessages {
    ERRO_DE_SISTEMA("Erro de Sistema"),
    ERRO_INTERNO("Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema " +
        "persistir, entre em contato com o administrador do sistema."),
    CAMPOS_INVALIDOS("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."),
    DADOS_INVALIDOS("Dados inválidos"),
    DETALHE_NAO_DISPONIVEL("Detalhe não disponível"),
    FORMATO_INVALIDO("O campo '%s' foi atribuído com o valor '%s', que não é válido para o tipo " +
        "esperado (%s). Por favor, corrija e forneça um valor compatível."),
    REQUISICAO_INVALIDA("Requisição Inválida"),
    PROPRIEDADE_NAO_EXISTE("A propriedade %s não existe. Corrija ou remova essa propriedade e " +
        "tente novamente"),
    CORPO_REQUISICAO_INVALIDO("O corpo da requisição está inválido. Verifique erro de sintaxe."),
    PARAMETRO_URL_INVALIDO("O parâmetro '%s' na URL possui o valor '%s', que não é válido para o " +
        "tipo esperado (%s). Por favor, corrija-o e forneça um valor válido."),
    TIPO_PARAMETRO_INVALIDO_URL("Tipo de Parâmetro Inválido na URL"),
    RECURSO_INVALIDO("O recurso '%s' que você tentou acessar, é inexistente."),
    RECURSO_NAO_ENCONTRADO("Recurso não Encontrado"),
    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada"),
    VIOLACAO_DE_REGRA_DE_NEGOCIO("Violação de regra de negócio"),
    NAO_SUPORTADO("Método Não Suportado"),
    METODO_NAO_SUPORTADO("O método HTTP '%s' não é suportado para este recurso"),
    CREDITO_INSUFICIENTE("Saldo insuficiente para realizar a operação"),
    LIMITE_EXCEDIDO("Limite de crédito excedido");

    private final String mensagem;

    ApiErrorMessages(String mensagem) {
        this.mensagem = mensagem;
    }

}
