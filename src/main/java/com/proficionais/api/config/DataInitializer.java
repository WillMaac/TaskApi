package com.proficionais.api.config;

import com.proficionais.api.entity.Usuario;
import com.proficionais.api.enums.Perfil;
import com.proficionais.api.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner criarSuperAdmin(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (usuarioRepository.existsByEmail("admin@taskapi.com")) {
                return;
            }

            Usuario usuario = new Usuario();

            usuario.setNome("Administrador");
            usuario.setCpf("52998224725");
            usuario.setEmail("admin@taskapi.com");
            usuario.setSenha(passwordEncoder.encode("123456"));
            usuario.setPerfil(Perfil.SUPER_ADMIN);

            usuarioRepository.save(usuario);

            System.out.println("SUPER_ADMIN inicial criado.");
        };
    }
}