package br.com.desafios.kalnascimento.api_votacao.infra.handlers.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponseDto {

    private int status;
    private String error;
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private List<String> details;

}
