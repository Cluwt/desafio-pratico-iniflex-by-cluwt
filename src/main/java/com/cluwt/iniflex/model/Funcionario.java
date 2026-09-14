package com.cluwt.iniflex.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Funcionario extends Pessoa {

    private BigDecimal salario;
    private final String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        if (salario == null || salario.signum() < 0) {
            throw new IllegalArgumentException("Salário não pode ser nulo ou negativo");
        }
        if (funcao == null || funcao.isBlank()) {
            throw new IllegalArgumentException("Função não pode ser nula ou vazia");
        }
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void aumentarSalario(BigDecimal percentual) {
        if (percentual == null || percentual.signum() < 0) {
            throw new IllegalArgumentException("Percentual de aumento não pode ser nulo ou negativo");
        }
        BigDecimal fator = BigDecimal.ONE.add(percentual);
        this.salario = this.salario.multiply(fator).setScale(2, RoundingMode.HALF_UP);
    }
}
