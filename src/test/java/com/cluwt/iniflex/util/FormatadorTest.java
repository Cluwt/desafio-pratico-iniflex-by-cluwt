package com.cluwt.iniflex.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatadorTest {

    @Test
    void formatarDataRetornaFormatoBrasileiro() {
        assertEquals("18/10/2000", Formatador.formatarData(LocalDate.of(2000, 10, 18)));
    }

    @Test
    void formatarMoedaRetornaFormatoBrasileiro() {
        // O JDK usa espaço fixo ( ) entre "R$" e o valor no locale pt-BR, não um espaço comum.
        assertEquals("R$ 2.009,44", Formatador.formatarMoeda(new BigDecimal("2009.44")));
    }

    @Test
    void formatarNumeroRetornaFormatoBrasileiro() {
        assertEquals("1,66", Formatador.formatarNumero(new BigDecimal("1.66")));
    }
}
