package br.com.desafios.kalnascimento.api_votacao.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarPautaRequestDto(

        @NotNull(message = "{validation.not.null}")
        String nome,

        @NotNull(message = "{validation.not.null}")
        String descricao
) {
}
