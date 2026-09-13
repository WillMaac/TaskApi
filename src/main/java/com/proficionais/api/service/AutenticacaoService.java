package com.proficionais.api.service;

import com.proficionais.api.dto.LoginRequest;
import com.proficionais.api.entity.Usuario;
import com.proficionais.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AutenticacaoService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AutenticacaoService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtEncoder jwtEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public String autenticar(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("E-mail ou senha inválidos.")
                );

        if (!passwordEncoder.matches(
                request.getSenha(),
                usuario.getSenha()
        )) {
            throw new IllegalArgumentException(
                    "E-mail ou senha inválidos."
            );
        }

        Instant agora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("task-api")
                .subject(usuario.getEmail())
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(3600))
                .claim("perfil", usuario.getPerfil().name())
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}