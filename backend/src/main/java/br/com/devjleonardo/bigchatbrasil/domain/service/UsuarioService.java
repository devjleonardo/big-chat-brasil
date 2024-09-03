package br.com.devjleonardo.bigchatbrasil.domain.service;

import br.com.devjleonardo.bigchatbrasil.core.util.LogUtil;
import br.com.devjleonardo.bigchatbrasil.core.util.MensagensValidacao;
import br.com.devjleonardo.bigchatbrasil.domain.exception.RegraNegocioException;
import br.com.devjleonardo.bigchatbrasil.domain.exception.UsuarioNaoEncontradoException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Pessoa;
import br.com.devjleonardo.bigchatbrasil.domain.model.Usuario;
import br.com.devjleonardo.bigchatbrasil.domain.model.enums.Perfil;
import br.com.devjleonardo.bigchatbrasil.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private static final String MSG_USUARIO_CRIADO_SUCESSO = "Usuário criado com sucesso";
    private static final String MSG_USUARIO_ATUALIZADO_SUCESSO = "Usuário atualizado com sucesso";
    private static final String CAMPO_EMAIL = "email";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario buscarUsuarioPorEmail(String email) {
        try {
            Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(email));

            LogUtil.registrarInfoComId(logger, "Usuario encontrado", usuario.getId());

            return usuario;
        } catch (UsuarioNaoEncontradoException e) {
            LogUtil.registrarExcecao(logger, e.getMessage(), e);
            throw e;
        }
    }

    public Usuario buscarUsuarioPorPessoa(Pessoa pessoa) {
        try {
            Usuario usuario = usuarioRepository.buscarUsuarioPorPessoa(pessoa)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(""));

            LogUtil.registrarInfoComId(logger, "Usuario encontrado", usuario.getId());

            return usuario;
        } catch (UsuarioNaoEncontradoException e) {
            LogUtil.registrarExcecao(logger, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Usuario criarOuAtualizarUsuario(Pessoa pessoa, String email, String senha, String perfil) {
        Perfil perfilUsuario = Perfil.valueOf(perfil.toUpperCase());

        return usuarioRepository.buscarUsuarioPorPessoa(pessoa)
            .map(usuarioExistente -> atualizarUsuario(usuarioExistente, email, senha, perfilUsuario))
            .orElseGet(() -> criarUsuario(pessoa, email, senha, perfilUsuario));
    }

    private Usuario criarUsuario(Pessoa pessoa, String email, String senha, Perfil perfil) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setPessoa(pessoa);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(senha));
        novoUsuario.setPerfil(perfil);

        validarEmail(novoUsuario);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        LogUtil.registrarInfoComId(logger, MSG_USUARIO_CRIADO_SUCESSO, usuarioSalvo.getId());

        return usuarioSalvo;
    }

    private Usuario atualizarUsuario(Usuario usuarioExistente, String email, String senha, Perfil perfil) {
        usuarioExistente.setEmail(email);
        usuarioExistente.setSenha(passwordEncoder.encode(senha));
        usuarioExistente.setPerfil(perfil);

        validarEmail(usuarioExistente);

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

        LogUtil.registrarInfoComId(logger, MSG_USUARIO_ATUALIZADO_SUCESSO, usuarioAtualizado.getId());

        return usuarioAtualizado;
    }

    private void validarEmail(Usuario usuario) {
        usuarioRepository.buscarUsuarioPorEmail(usuario.getEmail())
            .ifPresent(usuarioExistente -> {
                if (usuario.isNovo() || !usuarioExistente.equals(usuario)) {
                    throw new RegraNegocioException(
                        MensagensValidacao.gerarMensagemCampoExistente(CAMPO_EMAIL, usuario.getEmail())
                    );
                }
            });
    }

}
