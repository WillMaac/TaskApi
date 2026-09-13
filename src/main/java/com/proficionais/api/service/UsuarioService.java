package com.proficionais.api.service;

import com.proficionais.api.dto.UsuarioAtualizacaoRequest;
import com.proficionais.api.dto.UsuarioCadastroRequest;
import com.proficionais.api.dto.UsuarioResponse;
import com.proficionais.api.entity.Usuario;
import com.proficionais.api.exception.BadRequestException;
import com.proficionais.api.exception.UsuarioNaoEncontradoException;
import com.proficionais.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Usuario cadastrarUsuario(UsuarioCadastroRequest request) {

        if (usuarioRepository.existsByCpf(request.getCpf())) {
            throw new BadRequestException("CPF já cadastrado.");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("E-mail já cadastrado.");
        }

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

    public UsuarioResponse buscarUsuarioPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuário não encontrado."
                ));

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setCpf(usuario.getCpf());
        response.setEmail(usuario.getEmail());
        response.setPerfil(usuario.getPerfil());

        return response;
    }

    @Transactional
    public UsuarioResponse atualizarUsuario(
            Long id,
            UsuarioAtualizacaoRequest request
    ) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuário não encontrado."
                ));

        if (usuarioRepository.existsByCpfAndIdNot(request.getCpf(), id)) {
            throw new BadRequestException("CPF já cadastrado.");
        }

        if (usuarioRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new BadRequestException("E-mail já cadastrado.");
        }

        usuario.setNome(request.getNome());
        usuario.setCpf(request.getCpf());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setPerfil(request.getPerfil());

        usuarioRepository.save(usuario);

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setCpf(usuario.getCpf());
        response.setEmail(usuario.getEmail());
        response.setPerfil(usuario.getPerfil());

        return response;
    }

    @Transactional
    public void excluirUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(
                        "Usuário não encontrado."
                ));

        usuarioRepository.delete(usuario);
    }
}