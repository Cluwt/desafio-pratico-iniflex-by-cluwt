package com.cluwt.iniflex.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Utilitário de formatação de datas e valores numéricos no padrão brasileiro (pt-BR).
 */
public final class Formatador {

    private static final Locale LOCALE_BR = Locale.of("pt", "BR");
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(LOCALE_BR);
    private static final DecimalFormat FORMATO_NUMERO = new DecimalFormat(
            "#,##0.00", DecimalFormatSymbols.getInstance(LOCALE_BR));

    private Formatador() {
    }

    public static String formatarData(LocalDate data) {
        return data.format(FORMATO_DATA);
    }

    public static String formatarMoeda(BigDecimal valor) {
        return FORMATO_MOEDA.format(valor);
    }

    public static String formatarNumero(BigDecimal valor) {
        return FORMATO_NUMERO.format(valor);
    }
}
