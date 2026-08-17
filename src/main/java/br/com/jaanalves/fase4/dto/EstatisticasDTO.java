package br.com.jaanalves.fase4.dto;


public class EstatisticasDTO {
    private Long totalPlanos;
    private double valorMedioMensal;

    public EstatisticasDTO(Long totalPlanos, double valorMedioMensal) {
        this.totalPlanos = totalPlanos;
        this.valorMedioMensal = valorMedioMensal;
    }

    public Long getTotalPlanos() {
        return totalPlanos;
    }

    public void setTotalPlanos(Long totalPlanos) {
        this.totalPlanos = totalPlanos;
    }

    public double getValorMedioMensal() {
        return valorMedioMensal;
    }

    public void setValorMedioMensal(double valorMedioMensal) {
        this.valorMedioMensal = valorMedioMensal;
    }
}
