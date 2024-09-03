package br.com.devjleonardo.bigchatbrasil.domain.repository;

import br.com.devjleonardo.bigchatbrasil.domain.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    @Query("SELECT m FROM Mensagem m WHERE m.cliente.id = :clienteId")
    List<Mensagem> buscarHistoricoEnvioPorCliente(Long clienteId);

    @Query("SELECT SUM(m.custo) FROM Mensagem m WHERE m.status = 'ENVIADA'")
    BigDecimal calcularReceitaTotalEnviadas();

}
