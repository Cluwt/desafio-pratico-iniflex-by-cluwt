package com.cluwt.iniflex.exception;

/**
 * Lançada quando uma operação busca um funcionário pelo nome e ele não existe na lista.
 */
public class FuncionarioNaoEncontradoException extends NegocioException {

    public FuncionarioNaoEncontradoException(String nome) {
        super("Funcionário não encontrado: " + nome);
    }
}
