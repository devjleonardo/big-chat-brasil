package br.com.devjleonardo.bigchatbrasil.api.openapi;

import br.com.devjleonardo.bigchatbrasil.api.dto.request.MensagemRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.response.MensagemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "SMS")
public interface SmsOpenApi {

    @Operation(
        summary = "Envia uma mensagem SMS",
        description = "Envia uma mensagem SMS para um um destinatário",
        responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro"))),
        }
    )
    String enviarSms(
        @RequestBody(
            description = "Objeto contendo os dados necessários para enviar uma mensagem SMS"
        )
        MensagemRequestDTO mensagemRequestDTO
    );

    @Operation(
        summary = "Busca o histórico de envio de SMS de um cliente",
        description = "Retorna o histórico de todas as mensagens SMS enviadas para um cliente específico",
        responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "400", description = "ID do cliente inválido",
                content = @Content(schema = @Schema(ref = "Erro"))),

            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    List<MensagemDTO> buscarHistoricoEnvio(
        @Parameter(
            description = "ID do cliente para o histórico ser recuperado",
            example = "1",
            required = true
        )
        Long clienteId
    );

}
