package br.com.devjleonardo.bigchatbrasil.core.security.service;

import br.com.devjleonardo.bigchatbrasil.core.security.exception.TokenException;
import br.com.devjleonardo.bigchatbrasil.domain.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.chave}")
    private String chaveSecreta;

    @Value("${jwt.expiracao}")
    private long tempoExpiracao;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = obterAlgoritmo();
            return JWT.create()
                .withIssuer("login-auth-api")
                .withSubject(usuario.getEmail())
                .withExpiresAt(calcularDataExpiracao())
                .sign(algoritmo);
        } catch (JWTCreationException e) {
            throw new TokenException("Erro ao gerar o token para o usuário: " + usuario.getEmail(), e);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algoritmo = obterAlgoritmo();
            return JWT.require(algoritmo)
                .withIssuer("login-auth-api")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            throw new TokenException("Erro ao validar o token", e);
        }
    }

    private Algorithm obterAlgoritmo() {
        return Algorithm.HMAC256(chaveSecreta);
    }

    private Instant calcularDataExpiracao() {
        return LocalDateTime.now().plusSeconds(tempoExpiracao).toInstant(ZoneOffset.of("-03:00"));
    }

}
