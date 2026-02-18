package br.com.desafios.kalnascimento.api_votacao.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.VotarRequestDto;
import br.com.desafios.kalnascimento.api_votacao.domain.services.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/voto")
@RequiredArgsConstructor
public class VotoController implements BaseController {

    private final VotoService votoService;

    @Operation(
            summary = "Votar em uma pauta.",
            description = "Api para votar uma pauta pelo id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Adiciona um voto para uma pauta em sessão."
            ),
    })
    @PostMapping
    public ResponseEntity<Void> votarUmaPauta(@Valid @RequestBody VotarRequestDto dto) {

        votoService.votar(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
