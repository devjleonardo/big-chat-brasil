package br.com.devjleonardo.bigchatbrasil.domain.model.enums;

import lombok.Getter;

@Getter
public enum TipoPlano {

    PRE_PAGO("Pré-pago"),
    POS_PAGO("Pós-pago");

    private final String descricao;

    TipoPlano(String descricao) {
        this.descricao = descricao;
    }

}