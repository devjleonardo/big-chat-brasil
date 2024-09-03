package br.com.devjleonardo.bigchatbrasil.core.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AutenticacaoResponseDTO {

    private String token;
    private Long usuarioId;
    private String perfil;
    private String nomePessoa;

}
