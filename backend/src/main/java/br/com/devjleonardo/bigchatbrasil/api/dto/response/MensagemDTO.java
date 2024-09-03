package br.com.devjleonardo.bigchatbrasil.api.dto.response;

import br.com.devjleonardo.bigchatbrasil.domain.model.enums.StatusMensagem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MensagemDTO {

    private Long id;
    private LocalDate dataEnvio;
    private String numeroDestino;
    private String texto;
    private StatusMensagem status;
    private BigDecimal custo;
    private Boolean isWhatsapp;

}
