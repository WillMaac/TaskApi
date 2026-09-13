package com.proficionais.api.dto;

import com.proficionais.api.enums.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.proficionais.api.validation.CpfValido;
import jakarta.validation.constraints.Size;

public class UsuarioCadastroRequest {
    @NotBlank
    private String nome;

    @NotBlank
    @CpfValido
    private String cpf;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotNull
    private Perfil perfil;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}