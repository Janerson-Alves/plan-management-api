package br.com.jaanalves.fase4.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


// Entidades para criação da tabela tb_planos com table
@Entity
@Table(name = "tb_planos")
public class PlanoTelefonia {
    // Marcador de ID no banco, e gera um id  automaticamente no banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // O campo não pode ser vazio, será validado com @valid no Controller
    @NotBlank(message = "O nome do plano é obrigatório")
    private String nome;
    // O campo não pode ser negativo, será validado com @valid no Controller
    @Positive(message = "A franquia deve ser maior que zero")
    private int franquiaGb;
    // O campo não pode ser negativo, será validado com @valid no Controller
    @Positive(message = "O valor mensal deve ser maior que zero")
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

    public void setFranquiaGb(int franquaGb) {
        this.franquiaGb = franquiaGb;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        this.valorMensal = valorMensal;
    }
}
