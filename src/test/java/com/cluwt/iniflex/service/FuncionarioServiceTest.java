package com.cluwt.iniflex.service;

import com.cluwt.iniflex.exception.FuncionarioNaoEncontradoException;
import com.cluwt.iniflex.model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FuncionarioServiceTest {

    private List<Funcionario> funcionarios;

    @BeforeEach
    void montarLista() {
        funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
    }

    @Test
    void removerPorNomeRemoveFuncionarioExistente() {
        FuncionarioService.removerPorNome(funcionarios, "João");

        assertEquals(3, funcionarios.size());
        assertTrue(funcionarios.stream().noneMatch(f -> f.getNome().equals("João")));
    }

    @Test
    void removerPorNomeLancaExcecaoQuandoNaoEncontrado() {
        assertThrows(FuncionarioNaoEncontradoException.class, () ->
                FuncionarioService.removerPorNome(funcionarios, "Inexistente"));
    }

    @Test
    void agruparPorFuncaoAgrupaCorretamente() {
        Map<String, List<Funcionario>> porFuncao = FuncionarioService.agruparPorFuncao(funcionarios);

        assertEquals(2, porFuncao.get("Operador").size());
        assertEquals(1, porFuncao.get("Coordenador").size());
        assertEquals("Caio", porFuncao.get("Coordenador").get(0).getNome());
    }

    @Test
    void filtrarPorMesNascimentoFiltraCorretamente() {
        List<Funcionario> aniversariantes = FuncionarioService.filtrarPorMesNascimento(funcionarios, 10, 12);

        assertEquals(1, aniversariantes.size());
        assertEquals("Maria", aniversariantes.get(0).getNome());
    }

    @Test
    void funcionarioMaisVelhoRetornaCorreto() {
        Funcionario maisVelho = FuncionarioService.funcionarioMaisVelho(funcionarios);

        assertEquals("Caio", maisVelho.getNome());
    }

    @Test
    void funcionarioMaisVelhoLancaExcecaoParaListaVazia() {
        assertThrows(IllegalStateException.class, () ->
                FuncionarioService.funcionarioMaisVelho(new ArrayList<>()));
    }

    @Test
    void ordenarPorNomeOrdenaCorretamenteComAcentos() {
        List<Funcionario> ordenados = FuncionarioService.ordenarPorNome(funcionarios);

        List<String> nomes = ordenados.stream().map(Funcionario::getNome).toList();
        assertEquals(List.of("Caio", "Heloísa", "João", "Maria"), nomes);
    }

    @Test
    void somaSalariosSomaCorretamente() {
        BigDecimal soma = FuncionarioService.somaSalarios(funcionarios);

        assertEquals(new BigDecimal("15736.81"), soma);
    }

    @Test
    void quantidadeSalariosMinimosCalculaCorretamente() {
        BigDecimal quantidade = FuncionarioService.quantidadeSalariosMinimos(new BigDecimal("2009.44"));

        assertEquals(new BigDecimal("1.66"), quantidade);
    }
}
