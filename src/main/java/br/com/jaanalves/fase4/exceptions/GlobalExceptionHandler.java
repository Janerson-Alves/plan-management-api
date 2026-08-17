package br.com.jaanalves.fase4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

// Escuta e interceptar os erros disparados na aplicação
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Intercepta erros de validação do @Valid (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarErroValidacao(MethodArgumentNotValidException ex) {
        // Pega todos os erros de campo e formata em uma mensagem legivel
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining(" | "));
        // Instancia um novo erro passanso as informações para ele.
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação nos Dados",
                mensagem
        );
        // Retorna o erro e mostra no body a mensagem de erro
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Intercepta erros caso o nome do plano já possua cadastro (duplicados)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResposta> tratarErroArgumento(IllegalArgumentException ex) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação nos Dados",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

    }

}
