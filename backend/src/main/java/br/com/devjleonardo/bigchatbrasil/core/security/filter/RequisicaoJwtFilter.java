package br.com.devjleonardo.bigchatbrasil.core.security.filter;

import br.com.devjleonardo.bigchatbrasil.core.security.service.CustomUserDetailsService;
import br.com.devjleonardo.bigchatbrasil.core.security.service.TokenService;
import br.com.devjleonardo.bigchatbrasil.core.util.LogUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class RequisicaoJwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequisicaoJwtFilter.class);

    private final TokenService tokenService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {
        String jwtToken = extrairToken(request);
        String email = null;

        if (jwtToken != null) {
            email = tokenService.validarToken(jwtToken);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            autenticarUsuario(email);
        }

        chain.doFilter(request, response);
    }

    private void autenticarUsuario(String email) {
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            if (email.equals(userDetails.getUsername())) {
                LogUtil.registrarInfo(log, "Token validado com sucesso para o usuário: " + email);

                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            LogUtil.registrarExcecao(log, "Erro ao autenticar o usuário: " + email, e);
        }
    }

    private String extrairToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        LogUtil.registrarAviso(log, "Token JWT não encontrado ou inválido no cabeçalho Authorization");
        return null;
    }

}
