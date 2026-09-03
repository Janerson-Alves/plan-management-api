package br.com.jaanalves.fase4.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class TokenServiceTest {
    // Instancia a classe
    @Autowired
    private TokenService tokenService;
    // Método de Teste
    @Test
    void deveGerarEValidarToken() {
        // Informações de um usuario cliente.
        UserDetails usuario = User
                .withUsername("cliente")
                .password("123456")
                .roles("USER")
                .build();

        // Pega o Token gerado para o usuario
        String token = tokenService.gerarToken(usuario);
        // Token não Pode ser Vazio
        Assertions.assertNotNull(token);
        // Pega o Username e valida o Token
        String username = tokenService.validarToken(token);
        // Verifica se na validação o usuario e igual ao cliente.
        Assertions.assertEquals("cliente", username);
    }


}
