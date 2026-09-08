package br.com.jaanalves.fase4.security;


import br.com.jaanalves.fase4.dto.ErroRespostaDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    // Injeta o ObjectMapper que o Spring Boot ja configurou por padrão
    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException
    {

        // pegando o status de sem autorizacao para aquele tipo de acesso.
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Instancia um erro com as informacoes.
        ErroRespostaDTO erro = new ErroRespostaDTO(
                HttpStatus.FORBIDDEN.value(),
                "Acesso Negado",
                "Você não possui permissão para acessar este recurso.",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        // Pega as informacoes do erro e mostra no payload, objeto transformado em json
        response.getWriter().write(objectMapper.writeValueAsString(erro));

    }
}
