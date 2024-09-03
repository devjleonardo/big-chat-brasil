package br.com.devjleonardo.bigchatbrasil.api.openapi;

import br.com.devjleonardo.bigchatbrasil.api.dto.request.ClienteRequestDTO;
import br.com.devjleonardo.bigchatbrasil.api.dto.response.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Clientes")
public interface ClienteOpenApi {

    @Operation(
        summary = "Lista todos os músicas",
        description = "Retorna todos os clientes cadastrados",
        responses = {
            @ApiResponse(responseCode = "200")
        }
    )
    List<ClienteDTO> listar();
    @Operation(
        summary = "Busca um cliente por ID",
        description = "Retorna um cliente específico com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "400", description = "ID do cliente inválido",
                content = @Content(schema = @Schema(ref = "Erro"))),

            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    ClienteDTO buscarPorId(
        @Parameter(
            description = "ID do cliente a ser recuperado",
            example = "1",
            required = true
        )
        Long id
    );

    @Operation(
        summary = "Cadastra um novo cliente",
        description = "Cadastra um novo cliente no sistema",
        responses = {
            @ApiResponse(responseCode = "201"),

            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    ClienteDTO cadastrar(
        @RequestBody(
            description = "Objeto contendo os dados necessários para o cadastro de um novo cliente"
        )
        ClienteRequestDTO clienteRequestDTO
    );

    @Operation(
        summary = "Atualiza um cliente existente",
        description = "Atualiza os detalhes de um cliente específica com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200"),

            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content(schema = @Schema(ref = "Erro"))),

            @ApiResponse(responseCode = "404", description = "Música não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    ClienteDTO atualizar(
        @Parameter(
            description = "ID da música a ser atualizada",
            example = "1",
            required = true
        )
        Long id,

        @RequestBody(
            description = "Objeto contendo os dados necessários para atualizar as informações da música existente"
        )
        ClienteRequestDTO clienteRequestDTO
    );

    @Operation(
        summary = "Remove um cliente por ID",
        description = "Remove um cliente específico com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "204"),

            @ApiResponse(responseCode = "404", description = "Música não encontrada",
                content = @Content(schema = @Schema(ref = "Erro")))
        }
    )
    void remover(
        @Parameter(
            description = "ID da música a ser removida",
            example = "1",
            required = true
        )
        Long id
    );

}
