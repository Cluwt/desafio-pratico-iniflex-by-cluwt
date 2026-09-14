package com.cluwt.iniflex.exception;

/**
 * Lançada ao tentar adicionar um funcionário cujo nome já existe na lista.
 */
public class FuncionarioDuplicadoException extends NegocioException {

    public FuncionarioDuplicadoException(String nome) {
        super("Já existe um funcionário cadastrado com o nome: " + nome);
    }
}
