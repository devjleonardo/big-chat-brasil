package br.com.devjleonardo.bigchatbrasil.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClienteDTO {

    private Long id;
    private String nomeEmpresa;
    private String cpfResponsavel;
    private String cnpj;
    private String tipoPlano;
    private BigDecimal saldo;
    private BigDecimal limite;
    private BigDecimal consumo;
    private String nomePessoa;
    private String telefonePessoa;
    private String emailUsuario;

}
