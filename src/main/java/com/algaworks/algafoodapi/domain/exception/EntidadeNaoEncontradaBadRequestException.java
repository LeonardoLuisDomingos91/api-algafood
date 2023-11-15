package com.algaworks.algafoodapi.domain.exception;
public class EntidadeNaoEncontradaBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1l;
    public EntidadeNaoEncontradaBadRequestException(String mensagem) {
        super(mensagem);
    }
}