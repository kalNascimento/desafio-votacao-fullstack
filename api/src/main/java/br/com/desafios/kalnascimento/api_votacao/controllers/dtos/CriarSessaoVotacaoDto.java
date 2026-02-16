package br.com.desafios.kalnascimento.api_votacao.controllers.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CriarSessaoVotacaoDto(

        @NotNull
        UUID idPauta,

        LocalDateTime dataHoraFinalizacao
) {
}
