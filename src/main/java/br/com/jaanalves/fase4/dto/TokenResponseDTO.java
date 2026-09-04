package br.com.jaanalves.fase4.dto;

import jakarta.validation.constraints.NotBlank;

// Response do Token
public class TokenResponseDTO {
    @NotBlank
    private String token;
    @NotBlank
    private String tipo;

    // Construtor
    public TokenResponseDTO(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
