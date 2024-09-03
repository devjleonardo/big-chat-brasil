package br.com.devjleonardo.bigchatbrasil.domain.service;

import org.springframework.stereotype.Component;

@Component
public class SimulacaoEnvioSmsService {

    public void enviar(String numeroDestino, String conteudoMensagem) {
        System.out.println("Simulando envio de SMS para " + numeroDestino + ": " + conteudoMensagem);
    }

}
