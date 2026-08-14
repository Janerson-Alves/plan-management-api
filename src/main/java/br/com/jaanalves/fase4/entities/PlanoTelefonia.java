package br.com.jaanalves.fase4.entities;

import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;


// Entidades para criação da tabela tb_planos com table
@Entity
@Table(name = "tb_planos")
public class PlanoTelefonia {
    // Marcador de ID no banco, e gera um id  automaticamente no banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private int franquiaGb;
    @Column(nullable = false)
    private double valorMensal;


    // Construtor vazio para que o Spring consiga desserializar o JSON recebido da Web
    public PlanoTelefonia() {

    }

    // Construtor completo com Getters e Setters
    public PlanoTelefonia(Long id, String nome, int franquiaGb, double valorMensal) {
        this.id = id;
        this.nome = nome;
        this.franquiaGb = franquiaGb;
        this.valorMensal = valorMensal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFranquiaGb() {
        return franquiaGb;
    }

    public void setFranquiaGb(int franquiaGb) {
        this.franquiaGb = franquiaGb;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        this.valorMensal = valorMensal;
    }

    public ResponseEntity<PlanoTelefonia> map() {
        return null;
    }
}
