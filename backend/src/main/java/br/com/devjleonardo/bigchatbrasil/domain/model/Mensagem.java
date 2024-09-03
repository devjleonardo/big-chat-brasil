package br.com.devjleonardo.bigchatbrasil.domain.model;

import br.com.devjleonardo.bigchatbrasil.domain.model.enums.StatusMensagem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String numeroDestino;

    @Column(nullable = false)
    private Boolean isWhatsapp;

    @Column(length = 160, nullable = false)
    private String texto;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private StatusMensagem status;

    @Column(precision = 10, scale = 2)
    private BigDecimal custo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDate dataEnvio;

    @Override
    public boolean equals(Object outraMensagem) {
        if (this == outraMensagem) {
            return true;
        }

        if (outraMensagem instanceof Mensagem mensagem) {
            return Objects.equals(id, mensagem.id)
                && Objects.equals(numeroDestino, mensagem.numeroDestino)
                && Objects.equals(cliente, mensagem.cliente);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroDestino, cliente);
    }

}