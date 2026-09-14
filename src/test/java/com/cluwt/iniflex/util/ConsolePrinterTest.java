package com.cluwt.iniflex.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsolePrinterTest {

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
    void imprimirTituloExibeOTextoDentroDaCaixa() {
        ConsolePrinter.imprimirTitulo("Teste de Título");

        assertTrue(saidaCapturada.toString().contains("Teste de Título"));
    }

    @Test
    void imprimirTabelaExibeCabecalhoEDados() {
        ConsolePrinter.imprimirTabela(
                new String[] { "Nome", "Função" },
                List.<String[]>of(new String[] { "Maria", "Operador" }));

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains("Nome"));
        assertTrue(saida.contains("Maria"));
        assertTrue(saida.contains("Operador"));
    }

    @Test
    void imprimirTabelaComListaVaziaNaoLancaExcecaoEAvisaAusenciaDeRegistros() {
        ConsolePrinter.imprimirTabela(new String[] { "Nome" }, List.<String[]>of());

        assertTrue(saidaCapturada.toString().contains("nenhum registro encontrado"));
    }

    @Test
    void imprimirTabelaComNomeLongoNaoQuebraAAlinhamento() {
        String nomeBemLongo = "Um Nome Extremamente Longo Para Testar A Largura Dinamica Da Coluna";

        ConsolePrinter.imprimirTabela(
                new String[] { "Nome" },
                List.<String[]>of(new String[] { nomeBemLongo }));

        assertTrue(saidaCapturada.toString().contains(nomeBemLongo));
    }

    @Test
    void imprimirLinhaExibeOTexto() {
        ConsolePrinter.imprimirLinha("Total: R$ 100,00");

        assertTrue(saidaCapturada.toString().contains("Total: R$ 100,00"));
    }
}
