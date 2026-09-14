package com.cluwt.iniflex.util;

import java.util.List;

/**
 * Utilitário de apresentação para o console. Não conhece nenhuma classe de domínio:
 * recebe apenas strings já formatadas, calcula a largura das colunas a partir dos
 * dados recebidos e nunca assume tamanhos fixos, o que evita quebras com listas
 * vazias, nomes muito curtos ou muito longos.
 */
public final class ConsolePrinter {

    private static final int LARGURA_MAXIMA = 100;

    private ConsolePrinter() {
    }

    public static void imprimirTitulo(String titulo) {
        int largura = Math.min(LARGURA_MAXIMA, Math.max(titulo.length() + 4, 20));
        System.out.println();
        System.out.println("╔" + "═".repeat(largura - 2) + "╗");
        System.out.println("║" + centralizar(titulo, largura - 2) + "║");
        System.out.println("╚" + "═".repeat(largura - 2) + "╝");
    }

    public static void imprimirTabela(String[] cabecalhos, List<String[]> linhas) {
        int[] larguras = new int[cabecalhos.length];
        for (int i = 0; i < cabecalhos.length; i++) {
            larguras[i] = cabecalhos[i].length();
        }
        for (String[] linha : linhas) {
            for (int i = 0; i < linha.length && i < larguras.length; i++) {
                larguras[i] = Math.max(larguras[i], linha[i].length());
            }
        }

        imprimirBordaTabela(larguras, "┌", "┬", "┐");
        imprimirLinhaTabela(cabecalhos, larguras);
        imprimirBordaTabela(larguras, "├", "┼", "┤");

        if (linhas.isEmpty()) {
            System.out.println("│ nenhum registro encontrado" + " ".repeat(Math.max(0, somaLargura(larguras) - 26)) + "│");
        } else {
            for (String[] linha : linhas) {
                imprimirLinhaTabela(linha, larguras);
            }
        }

        imprimirBordaTabela(larguras, "└", "┴", "┘");
    }

    public static void imprimirLinha(String texto) {
        System.out.println("• " + texto);
    }

    private static void imprimirBordaTabela(int[] larguras, String esquerda, String meio, String direita) {
        StringBuilder sb = new StringBuilder(esquerda);
        for (int i = 0; i < larguras.length; i++) {
            sb.append("─".repeat(larguras[i] + 2));
            sb.append(i == larguras.length - 1 ? direita : meio);
        }
        System.out.println(sb);
    }

    private static void imprimirLinhaTabela(String[] valores, int[] larguras) {
        StringBuilder sb = new StringBuilder("│");
        for (int i = 0; i < larguras.length; i++) {
            String valor = i < valores.length ? valores[i] : "";
            sb.append(" ").append(alinharEsquerda(valor, larguras[i])).append(" │");
        }
        System.out.println(sb);
    }

    private static String alinharEsquerda(String texto, int largura) {
        if (texto.length() >= largura) {
            return texto;
        }
        return texto + " ".repeat(largura - texto.length());
    }

    private static String centralizar(String texto, int largura) {
        if (texto.length() >= largura) {
            return texto.substring(0, largura);
        }
        int espacoTotal = largura - texto.length();
        int esquerda = espacoTotal / 2;
        int direita = espacoTotal - esquerda;
        return " ".repeat(esquerda) + texto + " ".repeat(direita);
    }

    private static int somaLargura(int[] larguras) {
        int soma = 0;
        for (int largura : larguras) {
            soma += largura + 3;
        }
        return soma;
    }
}
