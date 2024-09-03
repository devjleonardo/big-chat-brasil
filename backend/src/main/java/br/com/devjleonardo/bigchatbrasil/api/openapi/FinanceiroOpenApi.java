package br.com.devjleonardo.bigchatbrasil.api.openapi;

import br.com.devjleonardo.bigchatbrasil.api.dto.request.CreditoRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.request.LimiteRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.request.PlanoRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.response.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.Map;

@Tag(name = "Financeiro")
public interface FinanceiroOpenApi {

    @Operation(
        summary = "Inclui créditos na conta de um cliente",
        description = "Adiciona um valor específico de crédito na conta de um cliente existente",
        responses = {
            @ApiResponse(responseCode = "200")
        }
    )
    String incluirCredito(
        @RequestBody(
            description = "Objeto contendo os dados necessários para incluir créditos na conta de um cliente"
        )
        CreditoRequestDTO creditoRequestDTO
    );

    @Operation(
        summary = "Consulta o saldo de um cliente",
        description = "Retorna o saldo atual da conta de um cliente com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "400", description = "ID do cliente inválido",
                content = @Content(schema = @Schema(ref = "Erro"))),

            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    BigDecimal consultarSaldo(
        @Parameter(
            description = "ID do cliente cujo saldo será consultado",
            example = "1",
            required = true
        )
        Long clienteId
    );

    @Operation(
        summary = "Altera o limite de crédito de um cliente",
        description = "Modifica o limite de crédito na conta de um cliente existente",
        responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro"))),

            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    String alterarLimite(
        @RequestBody(
            description = "Objeto contendo os dados necessários para alterar o limite de crédito de um cliente"
        )
        LimiteRequestDTO limiteRequestDTO
    );

    @Operation(
        summary = "Altera o plano de um cliente",
        description = "Modifica o plano de assinatura de um cliente existente",
        responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro"))),

            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    String alterarPlano(
        @RequestBody(
            description = "Objeto contendo os dados necessários para alterar o plano de um cliente"
        )
        PlanoRequestDTO planoRequestDTO
    );

    @Operation(
        summary = "Obtém o resumo financeiro",
        description = "Retorna um resumo financeiro com informações agregadas do sistema",
        responses = {
            @ApiResponse(responseCode = "200")
        }
    )
    Map<String, Object> resumoFinanceiro();

}
