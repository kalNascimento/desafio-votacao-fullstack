package br.com.desafios.kalnascimento.api_votacao.infra.handlers.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class RegraNegocioException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus status;

    public RegraNegocioException(String message) {
        super(message);
        this.errorCode = null;
        this.status = HttpStatus.BAD_REQUEST;
    }
}