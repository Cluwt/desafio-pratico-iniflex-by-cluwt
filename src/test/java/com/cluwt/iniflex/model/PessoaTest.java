package com.cluwt.iniflex.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PessoaTest {

    @Test
    void criaPessoaComDadosValidos() {
        Pessoa pessoa = new Pessoa("Maria", LocalDate.of(2000, 10, 18));

        assertEquals("Maria", pessoa.getNome());
        assertEquals(LocalDate.of(2000, 10, 18), pessoa.getDataNascimento());
    }

    @Test
    void construtorLancaExcecaoQuandoNomeNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Pessoa(null, LocalDate.of(2000, 10, 18)));
    }

    @Test
    void construtorLancaExcecaoQuandoNomeEmBranco() {
        assertThrows(IllegalArgumentException.class, () -> new Pessoa("   ", LocalDate.of(2000, 10, 18)));
    }

    @Test
    void construtorLancaExcecaoQuandoDataNascimentoNula() {
        assertThrows(IllegalArgumentException.class, () -> new Pessoa("Maria", null));
    }

    @Test
    void construtorLancaExcecaoQuandoDataNascimentoNoFuturo() {
        assertThrows(IllegalArgumentException.class, () -> new Pessoa("Maria", LocalDate.now().plusDays(1)));
    }
}
