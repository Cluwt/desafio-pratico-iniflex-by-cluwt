package com.cluwt.iniflex;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Roda o programa inteiro e confere a saída contra os valores calculados manualmente
 * a partir da tabela do enunciado — mesmo critério usado para validar o teste na mão:
 * não basta "parecer certo", o número tem que bater com o esperado.
 */
class PrincipalIntegrationTest {

    private final ByteArrayOutputStream saidaCapturada = new ByteArrayOutputStream();
    private PrintStream saidaOriginal;

    @BeforeEach
    void capturarSaida() {
        saidaOriginal = System.out;
        System.setOut(new PrintStream(saidaCapturada));
    }

    @AfterEach
    void restaurarSaida() {
        System.setOut(saidaOriginal);
    }

    @Test
    void programaCompletoProduzOsValoresEsperados() {
        Principal.main(new String[0]);
        String saida = saidaCapturada.toString();

        // 3.2 - João foi removido: o nome só aparece no título da seção 3.3
        // (que menciona a remoção), nunca como uma linha de dado nas tabelas
        int ocorrenciasDeJoao = saida.split("João", -1).length - 1;
        assertEquals(1, ocorrenciasDeJoao);
        assertTrue(saida.contains("Maria"));
        assertTrue(saida.contains("Helena"));

        // 3.4 - salários já com os 10% de aumento aplicados
        // (o JDK usa espaço fixo   entre "R$" e o valor no locale pt-BR)
        assertTrue(saida.contains("R$ 21.031,87")); // Miguel
        assertTrue(saida.contains("R$ 10.819,75")); // Caio

        // 3.8 - aniversariantes de outubro e dezembro
        assertTrue(saida.contains("18/10/2000")); // Maria
        assertTrue(saida.contains("14/10/1988")); // Miguel

        // 3.9 - funcionário mais velho: Caio, idade calculada na data em que o teste roda
        int idadeEsperadaDoCaio = Period.between(LocalDate.of(1961, 5, 2), LocalDate.now()).getYears();
        assertTrue(saida.contains("Nome: Caio"));
        assertTrue(saida.contains("Idade: " + idadeEsperadaDoCaio + " anos"));

        // 3.11 - soma total dos salários após o aumento
        assertTrue(saida.contains("R$ 50.906,82"));

        // 3.12 - quantidade de salários mínimos de pelo menos um funcionário
        assertTrue(saida.contains("1,82")); // Maria: 2210.38 / 1212.00
    }
}
