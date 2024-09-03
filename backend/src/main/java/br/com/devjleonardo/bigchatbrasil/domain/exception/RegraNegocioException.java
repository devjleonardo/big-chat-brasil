package br.com.devjleonardo.bigchatbrasil.domain.exception;

public class RegraNegocioException extends RuntimeException {

	public RegraNegocioException(String mensagem) {
		super(mensagem);
	}

}