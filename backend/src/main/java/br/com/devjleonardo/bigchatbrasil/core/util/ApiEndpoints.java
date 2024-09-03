package br.com.devjleonardo.bigchatbrasil.core.util;

public final class ApiEndpoints {

    public static final String BASE_API = "/api";
    public static final String API_VERSION = "/v1";

    // Define os endpoints principais
    public static final String CLIENTES = BASE_API + API_VERSION + "/clientes";
    public static final String SMS = BASE_API + API_VERSION + "/sms";
    public static final String FINANCEIRO = BASE_API + API_VERSION + "/financeiro";
    public static final String AUTENTICACAO = BASE_API + API_VERSION + "/autenticacao";


    private ApiEndpoints() {
    }

}
