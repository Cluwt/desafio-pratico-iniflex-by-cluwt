package com.cluwt.iniflex.exception;

/**
 * Lançada quando uma operação que exige ao menos um funcionário (ex.: encontrar o mais velho)
 * recebe uma lista vazia.
 */
public class ListaFuncionariosVaziaException extends NegocioException {

    public ListaFuncionariosVaziaException(String operacao) {
        super("Não é possível executar '" + operacao + "' em uma lista de funcionários vazia");
    }
}
