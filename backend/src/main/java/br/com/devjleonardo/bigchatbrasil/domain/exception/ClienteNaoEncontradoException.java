package br.com.devjleonardo.bigchatbrasil.domain.exception;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {
	public ClienteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ClienteNaoEncontradoException(Long clienteId) {
		this(String.format("Não existe um cadastro de cliente com ID o %d", clienteId));
	}
	
}
