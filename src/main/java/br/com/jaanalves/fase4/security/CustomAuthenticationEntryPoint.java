package br.com.jaanalves.fase4.security;

import br.com.jaanalves.fase4.dto.ErroRespostaDTO;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import java.time.LocalDateTime;

// tratamento de erro 401
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    // Injeta o ObjectMapper que o Spring Boot ja configurou por padrão
    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    // Sobrescreve o Metodo para tratar o erro
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException
    {
        // Pegandoo o status 401 de nao autorizado
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Instancia um erro com as informacoes.
        ErroRespostaDTO erro = new ErroRespostaDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Não Autorizado",
                "Token de acesso ausente, inválido ou expirado.",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        // Pega as informacoes do erro e mostra no payload, objeto transformado em json
        response.getWriter().write(objectMapper.writeValueAsString(erro));

    }

}
