package br.com.desafios.kalnascimento.api_votacao.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarPautaRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.PautaComboDto;
import br.com.desafios.kalnascimento.api_votacao.domain.services.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/pauta")
@RequiredArgsConstructor
public class PautaController implements BaseController {

    private final PautaService pautaService;

    @Operation(
            summary = "Criar pauta para votação.",
            description = "Cria a pauta que será colocada em votação em uma sessão."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Cria uma nova pauta e retorna o identificador único da pauta criada.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class)
                    )
            ),
    })
    @PostMapping
    public ResponseEntity<UUID> criarPauta(@Valid @RequestBody CriarPautaRequestDto dto) {

        var response = pautaService.criarPauta(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Listar pautas para combo.",
            description = "Lista pautas para a escolha na criação da sessão."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista todas as pautas para seleção.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PautaComboDto.class)
                    )
            ),
    })
    @GetMapping
    public ResponseEntity<List<PautaComboDto>> listarPautasCombo() {

        var response = pautaService.listarPautasCombo();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
