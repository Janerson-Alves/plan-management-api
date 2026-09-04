package br.com.jaanalves.fase4.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    // Define quais rotas são públicas ou protegidas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityFilter securityFilter) throws Exception {

        http
                // Desabilita o CSRF para a API REST
                .csrf(csrf -> csrf.disable())
                // Sessao stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define quem pode acessar cada rota
                .authorizeHttpRequests(auth -> auth
                        // Swagger: acesso público
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // Acesso anônimo ao login
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // Consulta GET : Liberados para USER E ADMIN
                        .requestMatchers(HttpMethod.GET,"/api/planos/**").hasAnyRole("USER", "ADMIN")
                        // Operações de escrita/exclusão (POST, PUT, DELETE): Restritas somente para ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/planos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/planos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/planos/**").hasRole("ADMIN")

                        // Outras rotas também precisam de autenticação
                        .anyRequest().authenticated()
                )
                // Insere o nosso filtro JWT antes do UsernamePasswordAuthenticationFilter
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

                // Ativa autenticação por usuário e senha
                // .httpBasic(Customizer.withDefaults());

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

    // Gerenciador de autenticação para validar usuario e senha
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
