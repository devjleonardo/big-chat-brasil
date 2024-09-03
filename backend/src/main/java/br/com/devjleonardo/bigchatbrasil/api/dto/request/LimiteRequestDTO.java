package br.com.devjleonardo.bigchatbrasil.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LimiteRequestDTO {

    @NotBlank
    private Long clienteId;

    @NotBlank
    private BigDecimal novoLimite;

}
