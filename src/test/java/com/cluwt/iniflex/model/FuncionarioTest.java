package com.cluwt.iniflex.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FuncionarioTest {

    @Test
    void aumentarSalarioAplicaPercentualCorretamente() {
        Funcionario funcionario = new Funcionario("Maria", LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"), "Operador");

        funcionario.aumentarSalario(new BigDecimal("0.10"));

        assertEquals(new BigDecimal("2210.38"), funcionario.getSalario());
    }

    @Test
    void construtorLancaExcecaoQuandoNomeEmBranco() {
        assertThrows(IllegalArgumentException.class, () ->
                new Funcionario("  ", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
    }

    @Test
    void construtorLancaExcecaoQuandoDataNascimentoNula() {
        assertThrows(IllegalArgumentException.class, () ->
                new Funcionario("Maria", null, new BigDecimal("2009.44"), "Operador"));
    }

    @Test
    void construtorLancaExcecaoQuandoSalarioNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("-1"), "Operador"));
    }

    @Test
    void construtorLancaExcecaoQuandoFuncaoEmBranco() {
        assertThrows(IllegalArgumentException.class, () ->
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), ""));
    }

    @Test
    void aumentarSalarioLancaExcecaoParaPercentualNegativo() {
        Funcionario funcionario = new Funcionario("Maria", LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"), "Operador");

        assertThrows(IllegalArgumentException.class, () ->
                funcionario.aumentarSalario(new BigDecimal("-0.10")));
    }
}
