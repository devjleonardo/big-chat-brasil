package br.com.devjleonardo.bigchatbrasil.domain.model.enums;

import lombok.Getter;

@Getter
public enum Perfil {

    ADMIN("Administrador"),
    FINANCEIRO("Financeiro"),
    CLIENTE("Cliente");

    private final String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

}