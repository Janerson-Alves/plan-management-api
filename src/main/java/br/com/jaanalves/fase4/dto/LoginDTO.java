package br.com.jaanalves.fase4.dto;

import jakarta.validation.constraints.NotBlank;

// Classe de Login
public class LoginDTO {
    // Atributos que não podem ser nulos
    @NotBlank
    private String username;
    @NotBlank
    private String senha;

    public LoginDTO(String username, String senha) {
        this.username = username;
        this.senha = senha;
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
