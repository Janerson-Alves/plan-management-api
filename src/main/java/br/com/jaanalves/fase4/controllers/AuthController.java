package br.com.jaanalves.fase4.controllers;

import br.com.jaanalves.fase4.dto.LoginDTO;
import br.com.jaanalves.fase4.dto.TokenResponseDTO;
import br.com.jaanalves.fase4.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // Injetando as dependencias
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    // Metodo de Login - POST
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        // Cria o Objeto de autenticação com Login e Senha
        var authToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getSenha());

        // Autentica via Spring Security (se falhar, lança o BadCredentialException automaticamente)
        var autentication = authenticationManager.authenticate(authToken);

        // Recupera o usuário autenticado e gera o JWT
        var usuario = (UserDetails) autentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(usuario);

        // Retorna ok instanciando um TokenResponseDTO passando o token e o tipo.
        return ResponseEntity.ok(new TokenResponseDTO(tokenJWT, "Bearer"));
    }




}
