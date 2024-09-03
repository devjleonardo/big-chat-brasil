package br.com.devjleonardo.bigchatbrasil.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanoRequestDTO {

    @NotBlank
    private Long clienteId;

    @NotBlank
    private String novoPlano;

}
