package br.com.devjleonardo.bigchatbrasil.domain.repository;

import br.com.devjleonardo.bigchatbrasil.domain.model.Cliente;
import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import br.com.devjleonardo.bigchatbrasil.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> buscarUsuarioPorEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.pessoa = :pessoa")
    Optional<Usuario> buscarUsuarioPorPessoa(Pessoa pessoa);

}
