package br.com.devjleonardo.bigchatbrasil.domain.model;

import br.com.devjleonardo.bigchatbrasil.domain.model.enums.TipoPlano;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String nomeEmpresa;

    @Column(length = 11, nullable = false)
    private String cpfResponsavel;

    @Column(length = 14, nullable = false, unique = true)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private TipoPlano tipoPlano;

    @Column(precision = 10, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal limite = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal consumo = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id", unique = true, nullable = false)
    private Pessoa pessoa;

    public boolean isNovo() {
        return id == null;
    }

    @Override
    public boolean equals(Object outroCliente) {
        if (this == outroCliente) {
            return true;
        }

        if (outroCliente instanceof Cliente cliente) {
            return Objects.equals(cnpj, cliente.cnpj)
                && Objects.equals(pessoa, cliente.pessoa);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj, pessoa);
    }

}
