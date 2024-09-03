package br.com.devjleonardo.bigchatbrasil.core.security.service;

import br.com.devjleonardo.bigchatbrasil.core.security.dto.AutenticacaoResponseDTO;
import br.com.devjleonardo.bigchatbrasil.core.security.dto.LoginRequestDTO;
import br.com.devjleonardo.bigchatbrasil.core.security.exception.SenhaIncorretaException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Usuario;
import br.com.devjleonardo.bigchatbrasil.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AutenticacaoResponseDTO autenticar(LoginRequestDTO loginRequestDTO) {
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(loginRequestDTO.getEmail());

        validarSenha(loginRequestDTO.getSenha(), usuario.getSenha(), loginRequestDTO.getEmail());

        String token = tokenService.gerarToken(usuario);


        return new AutenticacaoResponseDTO(token, usuario.getId(), usuario.getPerfil().name(),
            usuario.getPessoa().getNome());
    }

    private void validarSenha(String senhaFornecida, String senhaArmazenada, String email) {
        if (!passwordEncoder.matches(senhaFornecida, senhaArmazenada)) {
            throw new SenhaIncorretaException("Senha incorreta para o usuário: " + email);
        }
    }

}
