package br.com.devjleonardo.bigchatbrasil.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class ClienteRequestDTO {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    private String senha;

    private String perfil;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "\\+?[0-9 ]{10,20}")
    private String telefone;

    @NotBlank
    @CPF
    @Size(max = 11)
    private String cpfResponsavel;

    @NotBlank
    @CNPJ
    @Size(max = 14)
    private String cnpj;

    @NotBlank
    @Size(max = 150)
    private String nomeEmpresa;

    @NotBlank
    private String tipoPlano;

}
