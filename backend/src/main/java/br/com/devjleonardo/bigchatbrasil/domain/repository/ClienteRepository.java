package br.com.devjleonardo.bigchatbrasil.domain.repository;

import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.cnpj = :cnpj")
    Optional<Cliente> buscarClientePorCnpj(String cnpj);

}
