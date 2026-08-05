package br.com.jaanalves.fase4.controllers;

import br.com.jaanalves.fase4.dto.PlanoTelefonia;
import br.com.jaanalves.fase4.services.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    // Usa a anotação @PathVariable Long id para capturar o ID vindo da URL (ex: /api/planos/1) e chama service.buscarPorId(id).
    @GetMapping("/{id}")
    public PlanoTelefonia buscaPorId(@PathVariable Long id) {
        return planoService.buscarPorId(id);
    }

    // Endpoint Cadastro de Plano
    //Comportamento: Deve receber no corpo da requisição (Request Body) o JSON enviado pelo cliente,
    // converter para o DTO PlanoTelefonia, imprimir as informações recebidas no console
    // da aplicação e retornar uma String confirmando o cadastro.
    @PostMapping
    public String cadastrarPlano(@RequestBody PlanoTelefonia plano) {
        planoService.cadastrar(plano);
        return "Plano cadastrado com sucesso";
    }

}
