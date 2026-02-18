package br.com.desafios.kalnascimento.api_votacao.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AssociadoRequestDto(

        @NotBlank
        String nome,

        @NotBlank
        String cpf
) {
}
