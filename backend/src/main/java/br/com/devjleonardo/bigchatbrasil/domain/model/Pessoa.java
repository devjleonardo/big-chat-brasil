package br.com.devjleonardo.bigchatbrasil.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pessoa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 20, nullable = false, unique = true)
    private String telefone;

    @Override
    public boolean equals(Object outraPessoa) {
        if (this == outraPessoa) {
            return true;
        }

        if (outraPessoa instanceof Pessoa pessoa) {
            return Objects.equals(id, pessoa.id)
                && Objects.equals(telefone, pessoa.telefone);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telefone);
    }

}
