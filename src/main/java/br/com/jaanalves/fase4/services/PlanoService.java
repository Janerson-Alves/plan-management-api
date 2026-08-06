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

    // Atualiza o plano, Busca o plano na lista pelo id Se encontrar, altera seus campos
    // (nome, franquiaGb, valorMensal) para os novos valores recebidos e retorna o plano atualizado.
    // Se não encontrar, retorna null.
     public PlanoTelefonia atualizar(Long id, PlanoTelefonia planoAtualizado) {
         // Filtra o ID
        PlanoTelefonia filtroPeloId = planos.stream()
                .filter(plano -> plano.getId() == id)
                .findFirst()
                .orElse(null);

        // verifica se o Filtro e nulo.
        if (filtroPeloId == null) {
            return null;
        }

        // Atualizando os valores
        filtroPeloId.setNome(planoAtualizado.getNome());
        filtroPeloId.setFranquiaGb(planoAtualizado.getFranquiaGb());
        filtroPeloId.setValorMensal(planoAtualizado.getValorMensal());

        return filtroPeloId;
     }

     //Busca o plano na lista pelo id.
    //Se encontrar, remove-o da lista em memória e retorna true.
    //Se o id não existir, retorna false
    public boolean deletar(long id) {
        // Filtra o ID
        PlanoTelefonia filtroPeloId = planos.stream()
                .filter(plano -> plano.getId() == id)
                .findFirst()
                .orElse(null);
        // verifica se o Filtro e nulo.
        if (filtroPeloId == null) {
            return false;
        }
        // Remove o filtro realizado e retorna true para a remoção
        planos.remove(filtroPeloId);
        return true;
    }


}
