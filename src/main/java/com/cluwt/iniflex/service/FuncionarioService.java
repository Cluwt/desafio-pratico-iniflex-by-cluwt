package com.cluwt.iniflex.service;

import com.cluwt.iniflex.model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Collator;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Regras de negócio sobre listas de {@link Funcionario}. Mantém a classe
 * {@code Principal} enxuta, com um método aqui para cada requisito do teste.
 */
public final class FuncionarioService {

    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    private FuncionarioService() {
    }

    public static void removerPorNome(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(f -> f.getNome().equals(nome));
    }

    public static void aplicarAumento(List<Funcionario> funcionarios, BigDecimal percentual) {
        funcionarios.forEach(f -> f.aumentarSalario(percentual));
    }

    public static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        TreeMap::new,
                        Collectors.toList()));
    }

    public static List<Funcionario> filtrarPorMesNascimento(List<Funcionario> funcionarios, int... meses) {
        return funcionarios.stream()
                .filter(f -> {
                    int mes = f.getDataNascimento().getMonthValue();
                    for (int mesAlvo : meses) {
                        if (mes == mesAlvo) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public static Funcionario funcionarioMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow(() -> new IllegalStateException("Lista de funcionários vazia"));
    }

    public static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public static List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        Collator collatorPtBr = Collator.getInstance(Locale.of("pt", "BR"));
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome, collatorPtBr::compare))
                .collect(Collectors.toList());
    }

    public static BigDecimal somaSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal quantidadeSalariosMinimos(BigDecimal salario) {
        return salario.divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
    }
}
