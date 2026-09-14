package com.cluwt.iniflex.model;

import java.time.LocalDate;

public class Pessoa {

    private final String nome;
    private final LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (dataNascimento == null) {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula");
        }
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser uma data futura");
        }
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
