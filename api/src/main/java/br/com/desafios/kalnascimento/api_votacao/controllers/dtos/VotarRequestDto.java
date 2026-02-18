package br.com.desafios.kalnascimento.api_votacao.controllers.dtos;

import java.util.UUID;

import br.com.desafios.kalnascimento.api_votacao.domain.enums.VotoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record VotarRequestDto(

        @NotNull
        UUID idSessao,

        @NotNull
        VotoEnum voto,

        @NotNull
        AssociadoRequestDto associado
) {
}
