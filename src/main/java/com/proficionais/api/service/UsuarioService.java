package com.proficionais.api.service;
import com.proficionais.api.dto.UsuarioCadastroRequest;
import com.proficionais.api.entity.Usuario;
import com.proficionais.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.proficionais.api.dto.UsuarioResponse;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(UsuarioCadastroRequest request) {

        Usuario usuario = new Usuario();

        usuario.setNome(request.getNome());
        usuario.setCpf(request.getCpf());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setPerfil(request.getPerfil());

        return usuarioRepository.save(usuario);
    }

    public List<UsuarioResponse> listarUsuarios() {

        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> {
                    UsuarioResponse response = new UsuarioResponse();

                    response.setId(usuario.getId());
                    response.setNome(usuario.getNome());
                    response.setCpf(usuario.getCpf());
                    response.setEmail(usuario.getEmail());
                    response.setPerfil(usuario.getPerfil());

                    return response;
                })
                .toList();
    }
}