package br.com.jaanalves.fase4.repository;

import br.com.jaanalves.fase4.entities.PlanoTelefonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoRepository extends JpaRepository<PlanoTelefonia, Long> {
    // Busca planos pelo nome contendo determinado texto (case-insensitive)
    List<PlanoTelefonia> findByNomeContainingIgnoreCase(String nome);
    // Busca olanos cujo o valor mensal seja maior ou igual ao informado
    List<PlanoTelefonia> findByValorMensalLessThanEqual(double valorMaximo);
    // Verifica se existem nomes de planos duplicados
    boolean existsByNomeIgnoreCase(String nome);

}
