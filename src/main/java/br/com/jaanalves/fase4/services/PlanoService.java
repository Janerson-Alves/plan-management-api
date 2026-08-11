package br.com.jaanalves.fase4.services;

import br.com.jaanalves.fase4.dto.PlanoTelefonia;
import br.com.jaanalves.fase4.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// avisa ao Spring que esta classe é um componente de serviço e gerencia regras de negócio
@Service
public class PlanoService {

    private final List<PlanoTelefonia> planos = new ArrayList<>();

    // Injetando o repositorio
    @Autowired
    private PlanoRepository planoRepository;

    // Retorna a lista completa de planos do repository
    public List<PlanoTelefonia> listarTodos() {
        return planoRepository.findAll();
    }

    // Procura na lista o plano com o ID informado (pode usar um for tradicional ou stream().filter()).
    // Se não achar, pode retornar null.
    public PlanoTelefonia buscarPorId(long id) {
        return planoRepository.findById(id).orElse(null);
    }

    //Adiciona o plano na lista e o retorna repository
    public PlanoTelefonia cadastrar(PlanoTelefonia plano) {
        planoRepository.save(plano);
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
    //Se o id não existir, retorna false do repository
    public boolean deletar(long id) {
        // Filtra o ID pelo repositorio e retorna um valor booleano.
        boolean filtroPeloId = planoRepository.existsById(id);
        // Verifica se o valor retornando e False
        if (!filtroPeloId) {
            return false;
        }
        // Se for True, ele deleta o Id filtrado.
        planoRepository.deleteById(id);
        return true;
    }

    // Filtra pelo nome do plano
    public List<PlanoTelefonia> buscarPorNome(String nome) {
        return planoRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Filtra pelo valor seja maior ou igual ao informado
    public List<PlanoTelefonia> buscaporValorMaximo(double valorMax) {
        return planoRepository.findByValorMensalLessThanEqual(valorMax);
    }




}
