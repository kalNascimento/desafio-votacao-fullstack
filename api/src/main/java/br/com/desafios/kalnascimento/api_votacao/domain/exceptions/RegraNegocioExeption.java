package br.com.desafios.kalnascimento.api_votacao.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class RegraNegocioExeption extends RuntimeException {

    private final String errorCode;
    private final HttpStatus status;

}