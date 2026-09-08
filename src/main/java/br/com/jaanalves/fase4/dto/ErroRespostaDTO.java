package br.com.jaanalves.fase4.dto;

import java.time.LocalDateTime;

// Classe que vai receber a mensagem de erro record
public record ErroRespostaDTO(
        int status,
        String erro,
        String mensagem,
        String path,
        LocalDateTime timestamp
) {}
