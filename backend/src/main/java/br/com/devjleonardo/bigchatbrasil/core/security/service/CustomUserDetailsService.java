package br.com.devjleonardo.bigchatbrasil.core.security.service;

import br.com.devjleonardo.bigchatbrasil.core.util.LogUtil;
import br.com.devjleonardo.bigchatbrasil.domain.exception.UsuarioNaoEncontradoException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Usuario;
import br.com.devjleonardo.bigchatbrasil.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado com o email ";

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(USUARIO_NAO_ENCONTRADO + email));
            LogUtil.registrarInfo(logger, "Usuário encontrado " + email);
            return usuario;
        } catch (UsuarioNaoEncontradoException e) {
            LogUtil.registrarExcecao(logger, e.getMessage(), e);
            throw e;
        }
    }

}
