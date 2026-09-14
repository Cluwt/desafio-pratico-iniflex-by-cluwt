package com.cluwt.iniflex;

import com.cluwt.iniflex.model.Funcionario;
import com.cluwt.iniflex.service.FuncionarioService;
import com.cluwt.iniflex.util.ConsolePrinter;
import com.cluwt.iniflex.util.Formatador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe principal do Teste Prático Iniflex. Cada método privado corresponde
 * a um item do enunciado (3.1 a 3.12), executado em sequência a partir do main.
 */
public class Principal {

    public static void main(String[] args) {
        List<Funcionario> funcionarios = criarFuncionariosIniciais(); // 3.1

        FuncionarioService.removerPorNome(funcionarios, "João"); // 3.2

        imprimirTodos(funcionarios); // 3.3

        FuncionarioService.aplicarAumento(funcionarios, new BigDecimal("0.10")); // 3.4
        ConsolePrinter.imprimirTitulo("3.4 - Salários após aumento de 10%");
        imprimirTabelaFuncionarios(funcionarios);

        Map<String, List<Funcionario>> porFuncao = FuncionarioService.agruparPorFuncao(funcionarios); // 3.5
        imprimirAgrupadoPorFuncao(porFuncao); // 3.6

        imprimirAniversariantes(funcionarios); // 3.8

        imprimirMaisVelho(funcionarios); // 3.9

        imprimirOrdemAlfabetica(funcionarios); // 3.10

        imprimirSomaSalarios(funcionarios); // 3.11

        imprimirSalariosMinimos(funcionarios); // 3.12
    }

    private static List<Funcionario> criarFuncionariosIniciais() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    private static void imprimirTodos(List<Funcionario> funcionarios) {
        ConsolePrinter.imprimirTitulo("3.3 - Todos os funcionários (após remover João)");
        imprimirTabelaFuncionarios(funcionarios);
    }

    private static void imprimirAgrupadoPorFuncao(Map<String, List<Funcionario>> porFuncao) {
        ConsolePrinter.imprimirTitulo("3.6 - Funcionários agrupados por função");
        for (Map.Entry<String, List<Funcionario>> entrada : porFuncao.entrySet()) {
            ConsolePrinter.imprimirLinha("Função: " + entrada.getKey());
            imprimirTabelaFuncionarios(entrada.getValue());
        }
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios) {
        List<Funcionario> aniversariantes = FuncionarioService.filtrarPorMesNascimento(funcionarios, 10, 12);
        ConsolePrinter.imprimirTitulo("3.8 - Aniversariantes de outubro e dezembro");
        imprimirTabelaFuncionarios(aniversariantes);
    }

    private static void imprimirMaisVelho(List<Funcionario> funcionarios) {
        Funcionario maisVelho = FuncionarioService.funcionarioMaisVelho(funcionarios);
        int idade = FuncionarioService.calcularIdade(maisVelho.getDataNascimento());
        ConsolePrinter.imprimirTitulo("3.9 - Funcionário com maior idade");
        ConsolePrinter.imprimirLinha("Nome: " + maisVelho.getNome());
        ConsolePrinter.imprimirLinha("Idade: " + idade + " anos");
    }

    private static void imprimirOrdemAlfabetica(List<Funcionario> funcionarios) {
        List<Funcionario> ordenados = FuncionarioService.ordenarPorNome(funcionarios);
        ConsolePrinter.imprimirTitulo("3.10 - Funcionários em ordem alfabética");
        imprimirTabelaFuncionarios(ordenados);
    }

    private static void imprimirSomaSalarios(List<Funcionario> funcionarios) {
        BigDecimal soma = FuncionarioService.somaSalarios(funcionarios);
        ConsolePrinter.imprimirTitulo("3.11 - Soma total dos salários");
        ConsolePrinter.imprimirLinha("Total: " + Formatador.formatarMoeda(soma));
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios) {
        ConsolePrinter.imprimirTitulo("3.12 - Quantidade de salários mínimos por funcionário");
        List<String[]> linhas = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            BigDecimal quantidade = FuncionarioService.quantidadeSalariosMinimos(f.getSalario());
            linhas.add(new String[] { f.getNome(), Formatador.formatarNumero(quantidade) });
        }
        ConsolePrinter.imprimirTabela(new String[] { "Nome", "Salários mínimos" }, linhas);
    }

    private static void imprimirTabelaFuncionarios(List<Funcionario> funcionarios) {
        List<String[]> linhas = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            linhas.add(new String[] {
                    f.getNome(),
                    Formatador.formatarData(f.getDataNascimento()),
                    Formatador.formatarMoeda(f.getSalario()),
                    f.getFuncao()
            });
        }
        ConsolePrinter.imprimirTabela(new String[] { "Nome", "Data Nascimento", "Salário", "Função" }, linhas);
    }
}
