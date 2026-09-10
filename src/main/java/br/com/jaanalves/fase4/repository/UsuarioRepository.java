package br.com.jaanalves.fase4.repository;

import br.com.jaanalves.fase4.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Recebe o Login do perfil para consulta em banco
    Optional<Usuario> findByLogin(String login);
}
