package br.com.desafios.kalnascimento.api_votacao.controllers;

import br.com.desafios.kalnascimento.api_votacao.infra.handlers.dtos.ErrorResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses({
        @ApiResponse(
                responseCode = "400",
                description = "Solicitação inválida",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Recurso não encontrado",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "500",
                description = "Erro interno do servidor",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponseDto.class)
                )
        )
})
public interface BaseController {
}
