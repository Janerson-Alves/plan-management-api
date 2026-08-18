package br.com.jaanalves.fase4.services;

import br.com.jaanalves.fase4.dto.EstatisticasDTO;
import br.com.jaanalves.fase4.dto.PlanoDTO;
import br.com.jaanalves.fase4.entities.PlanoTelefonia;
import br.com.jaanalves.fase4.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // Retorna um Optional que pode ou não conter um plano
    public Optional<PlanoTelefonia> buscarPorId(long id) {

        return planoRepository.findById(id);
    }

    //Adiciona o plano na lista e o retorna repository
    // Verifica se ja possui um nome de plano ja cadastrado.
    public PlanoTelefonia cadastrar(PlanoDTO planoDTO) {
        // Verifica se o nome do plano já está cadastrado
        if (planoRepository.existsByNomeIgnoreCase(planoDTO.getNome())) {
            throw new IllegalArgumentException("Já existe um plano cadastrado com esse nome");
        }
        // Instancia um novo Plano
        PlanoTelefonia plano = new PlanoTelefonia();
        // Converte o PlanoDTO para Entity PlanoTelefonia
        plano.setNome(planoDTO.getNome());
        plano.setFranquiaGb(planoDTO.getFranquiaGb());
        plano.setValorMensal(planoDTO.getValorMensal());
        // Salva o plano
        planoRepository.save(plano);
        return plano;
    }

    // Atualiza o plano, Busca o plano na lista pelo id Se encontrar, altera seus campos
    // (nome, franquiaGb, valorMensal) para os novos valores recebidos e retorna o plano atualizado.
    // Se não encontrar, retorna null.
     public PlanoTelefonia atualizar(Long id, PlanoDTO planoDTO) {
         // Filtra o ID, caso não encontre, retorna nulo.
        PlanoTelefonia planoExistente = planoRepository.findById(id).orElse(null);

        //verifica se o Filtro e nulo.
        if (planoExistente == null) {
            return null;
        }

        // Atualiza os valores.
        planoExistente.setNome(planoDTO.getNome());
        planoExistente.setFranquiaGb(planoDTO.getFranquiaGb());
        planoExistente.setValorMensal(planoDTO.getValorMensal());
        // Salva os valores
        return planoRepository.save(planoExistente);
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

    // Calcular estatisticas qtd planos + valor medio dos planos.
    public EstatisticasDTO obterEstatisticas() {
        // Todos os planos
        List<PlanoTelefonia> planos = planoRepository.findAll();

        // Total de Planos
        long total = planos.size();

        // Media dos planos
        double media = planos.stream()
                .mapToDouble(PlanoTelefonia::getValorMensal)
                .average()
                .orElse(0.0);
        // Formata a media trazendo somente dois numeros depois do .
        double mediaFormatada = Math.round(media * 100.0) / 100.0;

        return new EstatisticasDTO(total, mediaFormatada);

    }




}
