package br.com.jaanalves.fase4.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // Define quais rotas são públicas ou protegidas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Desabilita o CSRF para a API REST
                .csrf(csrf -> csrf.disable())
                // Define quem pode acessar cada rota
                .authorizeHttpRequests(auth -> auth
                        // Swagger: acesso público
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // Consulta GET : Liberados para USER E ADMIN
                        .requestMatchers(HttpMethod.GET,"/api/planos/**").hasAnyRole("USER", "ADMIN")
                        // Operações de escrita/exclusão (POST, PUT, DELETE): Restritas somente para ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/planos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/planos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/planos/**").hasRole("ADMIN")

                        // Outras rotas também precisam de autenticação
                        .anyRequest().authenticated()
                )

                // Ativa autenticação por usuário e senha
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // Define o método usado para proteger as senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Cria usuários em memória para testes
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        // Usuário comum
        UserDetails cliente = User
                .withUsername("cliente")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .build();

        // Usuário administrador
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        // Guarda os usuários apenas na memória da aplicação
        return new InMemoryUserDetailsManager(cliente, admin);
    }
}
