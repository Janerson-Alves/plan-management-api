package br.com.jaanalves.fase4.controllers;

import br.com.jaanalves.fase4.dto.PlanoTelefonia;
import br.com.jaanalves.fase4.repository.PlanoRepository;
import br.com.jaanalves.fase4.services.PlanoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// Definindo a classe como controller e definindo um mapeamento base
@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    // Instanciando o Serviço
    @Autowired
    PlanoService planoService;
    @Autowired
    private PlanoRepository planoRepository;

    // EndPoint HealtCheck
    // Comportamento: Retorna uma String informando que a API de provisionamento está online.
    @GetMapping("/status")
    public String checarStatus() {
        return "API de aprovisionamento de planos se encontra Online.";
    }


    // Retorna a lista de todos os planos cadastrados via service.listarTodos()
    @GetMapping
    public List<PlanoTelefonia> listarTodos() {
        return planoService.listarTodos();
    }

    // Usa a anotação @PathVariable Long id para capturar o ID vindo da URL (ex: /api/planos/1) e
    // Usa o ResponseEntity para manipular o status HTTP e faz a busca pelo ID.
    // se o Id Existir ele retorna OK, caso contrario Not Found
    @GetMapping("/{id}")
    public ResponseEntity<PlanoTelefonia> buscaPorId(@PathVariable Long id) {
        return planoService.buscarPorId(id)
                .map(plano -> ResponseEntity.ok(plano))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint Cadastro de Plano
    //Comportamento: Deve receber no corpo da requisição (Request Body) o JSON enviado pelo cliente,
    // converter para o DTO PlanoTelefonia, imprimir as informações recebidas no console
    // da aplicação e retornar uma String confirmando o cadastro.
    // Usa o @Valid para validar os dados passados.
    @PostMapping
    public String cadastrarPlano(@Valid @RequestBody PlanoTelefonia plano) {
        planoService.cadastrar(plano);
        return "Plano cadastrado com sucesso";
    }

    // Endpoint de atualizar dados, nome, FranquiaGb e valorMensal
    // Usa a anotação @PathVariable Long id para capturar o ID vindo da URL (ex: /api/planos/1)
    // Usa a anotação @RequestBody para atualizar os valores do ID especifico, e retorna os valores atualizados
    // Usa o @Valid para validar os dados passados.
    @PutMapping("/{id}")
    public ResponseEntity<PlanoTelefonia> atualizarporId(@PathVariable Long id, @Valid @RequestBody PlanoTelefonia plano) {
        // Verifica se o ID nao existe
        if (!planoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // Caso o ID exista, atualiza e retorna OK 200
        PlanoTelefonia planoAtualizado = planoService.atualizar(id, plano);

        return ResponseEntity.ok(planoAtualizado);
    }

    // Endpoint de remover registros dos planos
    @DeleteMapping("/{id}")
    public ResponseEntity<PlanoTelefonia> removerPlanoId(@PathVariable Long id) {
        // Verifica se o ID nao existe
        if (!planoService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        // Caso o Id exista, remove e retorna 204
        planoService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    // Endpoint filtro por nome Controle
    @GetMapping("/buscar-por-nome")
    public List<PlanoTelefonia> buscarPorNome(@RequestParam String nome) {
        return planoService.buscarPorNome(nome);
    }

    // Endpoint Filtra pelo preço maximo de 100
    @GetMapping("/filtrar-preco")
    public List<PlanoTelefonia> buscaPorValor(@RequestParam double valorMaximo) {
        return planoService.buscaporValorMaximo(valorMaximo);
    }
}
