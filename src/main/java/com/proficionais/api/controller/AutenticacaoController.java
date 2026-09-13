package com.proficionais.api.controller;

import com.proficionais.api.dto.LoginRequest;
import com.proficionais.api.dto.LoginResponse;
import com.proficionais.api.service.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(
            AutenticacaoService autenticacaoService
    ) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        String token = autenticacaoService.autenticar(request);

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }
}