package br.com.jaanalves.fase4.repository;

import br.com.jaanalves.fase4.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    // Recebe o Nome do Perfil para consulta em banco
    Optional<Perfil> findByNome(String nome);
}
