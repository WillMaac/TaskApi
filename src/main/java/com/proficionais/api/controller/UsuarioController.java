package com.proficionais.api.controller;

import com.proficionais.api.dto.UsuarioCadastroRequest;
import com.proficionais.api.dto.UsuarioResponse;
import com.proficionais.api.entity.Usuario;
import com.proficionais.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioResponse cadastrarUsuario(
            @RequestBody @Valid UsuarioCadastroRequest request
    ) {
        Usuario usuario = usuarioService.cadastrarUsuario(request);

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setCpf(usuario.getCpf());
        response.setEmail(usuario.getEmail());
        response.setPerfil(usuario.getPerfil());

        return response;
    }

    @GetMapping
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }
}