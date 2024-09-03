package br.com.devjleonardo.bigchatbrasil.domain.repository;

import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p WHERE p.telefone = :telefone")
    Optional<Pessoa> buscarPessoaPorTelefone(String telefone);

}
