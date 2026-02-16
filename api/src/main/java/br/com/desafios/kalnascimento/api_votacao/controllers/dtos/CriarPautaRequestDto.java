package br.com.desafios.kalnascimento.api_votacao.controllers.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CriarPautaRequestDto(

        @NotNull(message = "{validation.not.null}")
        String nome,

        @NotNull(message = "{validation.not.null}")
        String descricao
) {
}
