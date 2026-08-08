package br.com.jaanalves.fase4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class PlanoTelefonia {
    private Long id;

    @NotBlank(message = "O nome do plano é obrigatório")
    private String nome;
    @Positive(message = "A franquia deve ser maior que zero")
    private int franquiaGb;
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
