package br.com.jaanalves.fase4.config;

import br.com.jaanalves.fase4.entities.Perfil;
import br.com.jaanalves.fase4.entities.Usuario;
import br.com.jaanalves.fase4.repository.PerfilRepository;
import br.com.jaanalves.fase4.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class CargaDadosConfig implements CommandLineRunner {

    // atributos
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public CargaDadosConfig(UsuarioRepository usuarioRepository,
                            PerfilRepository perfilRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Sobrescrevendo o metodo do implements
    // Transactional para garantir o vínculo @ManyToMany sem erros de sessão fechada no JPA
    @Transactional
    @Override
    public void run(String... args) {
        // Cadastra os perfis admin e user
        Perfil roleAdmin = perfilRepository.findByNome("ROLE_ADMIN")
                .orElseGet(() -> perfilRepository.save(new Perfil("ROLE_ADMIN")));
        Perfil roleUser = perfilRepository.findByNome("ROLE_USER")
                .orElseGet(() -> perfilRepository.save(new Perfil("ROLE_USER")));
        // Verifica se não existe um usuario admin cadastrado
        if (usuarioRepository.findByLogin("admin.telecom").isEmpty()) {
            // cadastra o usuario admin
            usuarioRepository.save(new Usuario(
                    "admin.telecom",
                    passwordEncoder.encode("admin123"),
                    Set.of(roleAdmin, roleUser)
            ));
            }
        // Verifica se não existe um usuario cliente cadastrado
        if (usuarioRepository.findByLogin("cliente.telecom").isEmpty()) {
            // cadastra o usuario telecom
            usuarioRepository.save(new Usuario(
                    "cliente.telecom",
                    passwordEncoder.encode("123456"),
                    Set.of(roleUser)
            ));
        }

    }

}
