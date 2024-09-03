package br.com.devjleonardo.bigchatbrasil.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemRequestDTO {

    @NotBlank
    private Long clienteId;

    @NotBlank
    private String numeroDestino;

    @NotBlank
    private Boolean isWhatsapp;

    @NotBlank
    private String texto;

}
