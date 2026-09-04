package br.com.jaanalves.fase4.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Diz para o Spring: "essa classe é um componente que você deve gerenciar"
@Component
public class SecurityFilter extends OncePerRequestFilter {

    // Serviço responsável por validar o JWT
    @Autowired
    TokenService tokenService;

    // Serviço usado para buscar o usuário pelo login
    @Autowired
    UserDetailsService userDetailsService;


    // Esse método é executado automaticamente
    // em TODA requisição que passa pelo Spring Security
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        // 1️⃣ Tenta pegar o TOKEN que veio na requisição
        var token = recuperarToken(request);


        // Só continua com a autenticação se encontrou um token
        if (token != null) {

            // 2️⃣ Verifica se o JWT é válido
            // Se for válido, retorna o LOGIN que está dentro do token
            var login = tokenService.validarToken(token);


            // Se o token foi válido e conseguimos pegar o login
            if (login != null) {

                // 3️⃣ Busca o usuário no banco
                // usando o login que veio do token
                UserDetails usuario =
                        userDetailsService.loadUserByUsername(login);


                // 4️⃣ Cria uma autenticação do Spring Security
                // usuario      → quem está logado
                // null         → não precisamos da senha aqui
                // authorities  → permissões/roles desse usuário
                var authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                usuario.getAuthorities()
                        );


                // 5️⃣ AVISA AO SPRING SECURITY:
                // "Esse usuário está autenticado!"
                // A partir daqui, o Spring consegue saber
                // quem está fazendo a requisição.
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }


        // 6️⃣ Continua o caminho normal da requisição
        // O filtro terminou o trabalho dele.
        // Então deixa a requisição passar para o próximo filtro
        // ou chegar no Controller.
        filterChain.doFilter(request, response);
    }


    // Método responsável por PEGAR O TOKEN do Header
    private String recuperarToken(HttpServletRequest request) {

        // Pega o valor do Header "Authorization"
        // Exemplo: Authorization: Bearer eyJhbGciOiJIUzI1...
        var authHeader = request.getHeader("authorization");


        // Se não existe Authorization
        // OU não começa com "Bearer "
        // significa que não encontramos um JWT válido no Header
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            return null;
        }

        // Remove o "Bearer "
        // e devolve somente o JWT
        //Antes: Bearer eyJhbGciOiJIUzI1...
        // Depois:eyJhbGciOiJIUzI1...
        return authHeader
                .replace("Bearer ", "")
                .trim();
    }
}


