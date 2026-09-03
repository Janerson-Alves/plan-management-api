package br.com.jaanalves.fase4.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    // Atributo com token
    @Value("${api.security.token.secret}")
    private String secret;


    // Metodo para gerar o Token
    public String gerarToken(UserDetails usuario) {
        // Configura o Algoritmo HMAC256 com chave secreta
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // Gera o Token
        return JWT.create()
                // Emissor
                .withIssuer("plan-manager-api")
                // Dono do Token
                .withSubject(usuario.getUsername())
                // Data de Expiração
                .withExpiresAt(gerarDataExpiracao())
                // Role do Usuario com Clain
                .withClaim("role", usuario.getAuthorities().iterator().next().getAuthority())
                // Assinar
                .sign(algorithm);
    }

    private Instant gerarDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String validarToken(String tokenJWT) {

        // Verifica se executou com sucesso a validação do token
        try {
            // Configura o Algoritmo HMAC256 com chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Retorna o Subject com nome do usuario contido no token.
            return JWT.require(algorithm)
                    .withIssuer("plan-manager-api")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        // Caso o token expirado ou adulterado.
        } catch (JWTVerificationException ex) {
            return null;
        }

    }

}
