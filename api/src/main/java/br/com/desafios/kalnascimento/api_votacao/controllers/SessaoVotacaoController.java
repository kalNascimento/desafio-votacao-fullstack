package br.com.desafios.kalnascimento.api_votacao.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarPautaRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoDto;
import br.com.desafios.kalnascimento.api_votacao.domain.services.SessaoVotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sessao-votacao")
@RequiredArgsConstructor
public class SessaoVotacaoController implements  BaseController {

    private final SessaoVotacaoService sessaoVotacaoService;

    @Operation(
            summary = "Criar a sessão de votação para pauta.",
            description = "Cria a sessão para a votação da pauta."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cria uma nova sessão de votação e retorna o identificador único da sessão criada.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class)
                    )
            ),
    })
    @PostMapping
    public ResponseEntity<UUID> criarSessaoVotacao(@Valid @RequestBody CriarSessaoVotacaoDto dto) {

        var response = sessaoVotacaoService.criarSessaoVotacao(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
