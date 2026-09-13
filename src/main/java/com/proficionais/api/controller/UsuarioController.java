package com.proficionais.api.controller;

import com.proficionais.api.dto.UsuarioAtualizacaoRequest;
import com.proficionais.api.dto.UsuarioCadastroRequest;
import com.proficionais.api.dto.UsuarioResponse;
import com.proficionais.api.entity.Usuario;
import com.proficionais.api.enums.Perfil;
import com.proficionais.api.exception.BadRequestException;
import com.proficionais.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/cadastro/gerente")
    public ResponseEntity<UsuarioResponse> cadastrarGerente(
            @RequestBody @Valid UsuarioCadastroRequest request
    ) {
        request.setPerfil(Perfil.GERENTE);

        Usuario usuario = usuarioService.cadastrarUsuario(request);

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setCpf(usuario.getCpf());
        response.setEmail(usuario.getEmail());
        response.setPerfil(usuario.getPerfil());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'GERENTE')")
    @PostMapping("/cadastro/profissional")
    public ResponseEntity<UsuarioResponse> cadastrarProfissional(
            @RequestBody @Valid UsuarioCadastroRequest request
    ) {
        if (request.getPerfil() == Perfil.SUPER_ADMIN
                || request.getPerfil() == Perfil.GERENTE) {

            throw new BadRequestException(
                    "Esta rota aceita apenas os perfis MEDICO, ENFERMEIRO ou ATENDENTE."
            );
        }

        Usuario usuario = usuarioService.cadastrarUsuario(request);

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setCpf(usuario.getCpf());
        response.setEmail(usuario.getEmail());
        response.setPerfil(usuario.getPerfil());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizarUsuario(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioAtualizacaoRequest request
    ) {
        return usuarioService.atualizarUsuario(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
    }
}