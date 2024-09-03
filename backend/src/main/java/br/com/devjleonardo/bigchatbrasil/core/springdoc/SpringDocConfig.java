package br.com.devjleonardo.bigchatbrasil.core.springdoc;

import br.com.devjleonardo.bigchatbrasil.api.exceptionhandler.ApiErrorResponse;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SpringDocConfig {

    private static final String RESPOSTA_REQUISICAO_INVALIDA = "RespostaRequisicaoInvalida";
    private static final String RESPOSTA_RECURSO_NAO_ENCONTRADO = "RespostaRecursoNaoEncontrado";
    private static final String RESPOSTA_NAO_ACEITAVEL = "RespostaNaoAceitavel";
    private static final String RESPOSTA_ERRO_INTERNO_SERVIDOR = "RespostaErroInternoServidor";

    private static final String DESCRICAO_REQUISICAO_INVALIDA = "Requisição inválida";
    private static final String DESCRICAO_RECURSO_NAO_ENCONTRADO = "Recurso não encontrado";
    private static final String DESCRICAO_NAO_ACEITAVEL = "Recurso possui representação que poderia ser aceita pelo consumidor";
    private static final String DESCRICAO_ERRO_INTERNO_SERVIDOR = "Erro interno no servidor";

    private static final String TITULO_API = "Big Chat Brasil API";
    private static final String VERSAO_API = "v1";
    private static final String DESCRICAO_API = "API do Big Chat Brasil ";

    private static final String NOME_TAG_CLIENTES = "Clientes";
    private static final String DESCRICAO_TAG_CLIENTES = "Gerenciamento de clientes";

    private static final String NOME_TAG_FINANCEIRO = "Financeiro";
    private static final String DESCRICAO_TAG_FINANCEIRO = "Gerenciamento financeiro";

    private static final String NOME_TAG_SMS = "SMS";
    private static final String DESCRICAO_TAG_SMS = "Envio de SMS";

    private static final String SCHEMA_ERRO_API = "Erro";

    private static final String STATUS_GET = "GET";
    private static final String STATUS_POST = "POST";
    private static final String STATUS_PUT = "PUT";
    private static final String STATUS_DELETE = "DELETE";
    private static final String STATUS_400 = "400";
    private static final String STATUS_404 = "404";
    private static final String STATUS_406 = "406";
    private static final String STATUS_500 = "500";

    @Bean
    public OpenAPI configurarOpenAPI() {
        return new OpenAPI()
            .info(criarInformacoesAPI())
            .tags(Arrays.asList(criarTagClientes(), criarTagFinanceiro(), criarTagSMS()))
            .components(new Components()
                .schemas(gerarSchemas())
                .responses(gerarRespostas())
            );
    }

    @Bean
    public OpenApiCustomizer customizarOpenAPI() {
        return openApi -> openApi.getPaths()
            .values()
            .forEach(pathItem -> pathItem.readOperationsMap()
                .forEach((httpMethod, operation) -> {
                    ApiResponses responses = operation.getResponses();
                    adicionarRespostasComuns(httpMethod.name(), responses);
                })
            );
    }

    private void adicionarRespostasComuns(String metodoHttp, ApiResponses respostas) {
        switch (metodoHttp) {
            case STATUS_GET:
                adicionarResposta(respostas, STATUS_406, RESPOSTA_NAO_ACEITAVEL);
                adicionarResposta(respostas, STATUS_500, RESPOSTA_ERRO_INTERNO_SERVIDOR);
                break;
            case STATUS_POST:
                adicionarResposta(respostas, STATUS_400, RESPOSTA_REQUISICAO_INVALIDA);
                adicionarResposta(respostas, STATUS_500, RESPOSTA_ERRO_INTERNO_SERVIDOR);
                break;
            case STATUS_PUT:
                adicionarResposta(respostas, STATUS_400, RESPOSTA_REQUISICAO_INVALIDA);
                adicionarResposta(respostas, STATUS_500, RESPOSTA_ERRO_INTERNO_SERVIDOR);
                break;
            case STATUS_DELETE:
                adicionarResposta(respostas, STATUS_404, RESPOSTA_RECURSO_NAO_ENCONTRADO);
                adicionarResposta(respostas, STATUS_500, RESPOSTA_ERRO_INTERNO_SERVIDOR);
                break;
            default:
                adicionarResposta(respostas, STATUS_500, RESPOSTA_ERRO_INTERNO_SERVIDOR);
                break;
        }
    }

    private void adicionarResposta(ApiResponses respostas, String codigoStatus, String referenciaResposta) {
        respostas.addApiResponse(codigoStatus, new ApiResponse().$ref(referenciaResposta));
    }

    private Info criarInformacoesAPI() {
        return new Info()
            .title(TITULO_API)
            .version(VERSAO_API)
            .description(DESCRICAO_API);
    }

    private Tag criarTagClientes() {
        return new Tag()
            .name(NOME_TAG_CLIENTES)
            .description(DESCRICAO_TAG_CLIENTES);
    }

    private Tag criarTagFinanceiro() {
        return new Tag()
            .name(NOME_TAG_FINANCEIRO)
            .description(DESCRICAO_TAG_FINANCEIRO);
    }

    private Tag criarTagSMS() {
        return new Tag()
            .name(NOME_TAG_SMS)
            .description(DESCRICAO_TAG_SMS);
    }

    private Map<String, Schema> gerarSchemas() {
        Map<String, Schema> mapSchemas = new HashMap<>();
        mapSchemas.putAll(ModelConverters.getInstance().read(ApiErrorResponse.class));
        mapSchemas.putAll(ModelConverters.getInstance().read(ApiErrorResponse.CampoComErro.class));
        return mapSchemas;
    }

    private Map<String, ApiResponse> gerarRespostas() {
        Map<String, ApiResponse> mapRespostas = new HashMap<>();
        Content conteudo = criarConteudoErroAPI();

        mapRespostas.put(RESPOSTA_REQUISICAO_INVALIDA, criarRespostaAPI(DESCRICAO_REQUISICAO_INVALIDA, conteudo));
        mapRespostas.put(RESPOSTA_RECURSO_NAO_ENCONTRADO, criarRespostaAPI(DESCRICAO_RECURSO_NAO_ENCONTRADO, conteudo));
        mapRespostas.put(RESPOSTA_NAO_ACEITAVEL, criarRespostaAPI(DESCRICAO_NAO_ACEITAVEL, conteudo));
        mapRespostas.put(RESPOSTA_ERRO_INTERNO_SERVIDOR, criarRespostaAPI(DESCRICAO_ERRO_INTERNO_SERVIDOR, conteudo));

        return mapRespostas;
    }

    private Content criarConteudoErroAPI() {
        return new Content()
            .addMediaType(APPLICATION_JSON_VALUE,
                new MediaType().schema(new Schema<>().$ref(SCHEMA_ERRO_API)));
    }

    private ApiResponse criarRespostaAPI(String descricao, Content conteudo) {
        return new ApiResponse()
            .description(descricao)
            .content(conteudo);
    }

}
