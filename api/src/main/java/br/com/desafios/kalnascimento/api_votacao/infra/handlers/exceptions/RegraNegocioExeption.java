package br.com.desafios.kalnascimento.api_votacao.infra.handlers.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegraNegocioExeption extends RuntimeException {

    private final String errorCode;
    private final HttpStatus status;

}