package com.cluwt.iniflex.exception;

/**
 * Base para exceções que representam violação de uma regra de negócio
 * (em oposição a argumentos inválidos, que usam {@link IllegalArgumentException}).
 */
public abstract class NegocioException extends RuntimeException {

    protected NegocioException(String mensagem) {
        super(mensagem);
    }
}
