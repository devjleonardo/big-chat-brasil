package br.com.devjleonardo.bigchatbrasil.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(String emailUsuario) {
        super(String.format("Não existe um cadastro de usuário com o email '%s'", emailUsuario));
    }

}
