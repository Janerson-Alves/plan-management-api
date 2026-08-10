package br.com.jaanalves.fase4.repository;

import br.com.jaanalves.fase4.dto.PlanoTelefonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoRepository extends JpaRepository<PlanoTelefonia, Long> {

}
