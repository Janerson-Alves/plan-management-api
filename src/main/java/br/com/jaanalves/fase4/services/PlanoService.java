package br.com.jaanalves.fase4.services;

import br.com.jaanalves.fase4.dto.PlanoTelefonia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// avisa ao Spring que esta classe é um componente de serviço e gerencia regras de negócio
@Service
public class PlanoService {

    private final List<PlanoTelefonia> planos = new ArrayList<>();


    // Retorna a lista completa de planos
    public List<PlanoTelefonia> listarTodos() {
        return planos;
    }

    // Procura na lista o plano com o ID informado (pode usar um for tradicional ou stream().filter()). Se não achar, pode retornar null.
    public PlanoTelefonia buscarPorId(long id) {
        return planos.stream()
                .filter(plano -> plano.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //Adiciona o plano na lista e o retorna
    public PlanoTelefonia cadastrar(PlanoTelefonia plano) {
        planos.add(plano);
        return plano;
    }
}
