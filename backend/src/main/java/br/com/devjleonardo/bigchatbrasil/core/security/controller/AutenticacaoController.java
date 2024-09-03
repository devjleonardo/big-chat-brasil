package br.com.devjleonardo.bigchatbrasil.core.security.controller;

import br.com.devjleonardo.bigchatbrasil.core.security.dto.AutenticacaoResponseDTO;
import br.com.devjleonardo.bigchatbrasil.core.security.dto.LoginRequestDTO;
import br.com.devjleonardo.bigchatbrasil.core.security.service.AutenticacaoService;
import br.com.devjleonardo.bigchatbrasil.core.util.ApiEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoints.AUTENTICACAO)
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    @PostMapping("/signin")
    public AutenticacaoResponseDTO autenticar(@RequestBody LoginRequestDTO loginRequestDTO) {
        return autenticacaoService.autenticar(loginRequestDTO);
    }

}
