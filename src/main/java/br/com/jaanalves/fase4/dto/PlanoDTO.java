package br.com.jaanalves.fase4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.web.PageableDefault;

public class PlanoDTO {
    // Campo obrigatorio
    @NotBlank(message = "O nome do plano é obrigatório")
    private String nome;
    // Campo obrigatorio
    @Positive(message = "A franquia deve ser maior que zero")
    private int franquiaGb;
    // Campo obrigatorio
    @Positive(message = "O valor mensal deve ser maior que zero.")
    private double valorMensal;

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
}
