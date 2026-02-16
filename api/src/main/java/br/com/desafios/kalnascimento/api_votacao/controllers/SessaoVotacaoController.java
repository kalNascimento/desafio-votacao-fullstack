package br.com.desafios.kalnascimento.api_votacao.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.PautaComboResponseDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.SessaoVotacaoResponseDto;
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
public class SessaoVotacaoController implements BaseController {

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
    public ResponseEntity<UUID> criarSessaoVotacao(@Valid @RequestBody CriarSessaoVotacaoRequestDto dto) {

        var response = sessaoVotacaoService.criarSessaoVotacao(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Listar sessão de votação para combo.",
            description = "Lista sessão de votação para a escolha na votação."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista todas as sessão de votação para seleção.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PautaComboResponseDto.class)
                    )
            ),
    })
    @GetMapping
    public ResponseEntity<Page<SessaoVotacaoResponseDto>> listarSessoesVotacao(
            @PageableDefault(
                    size = 10,
                    sort = "dataHoraFinalizacao",
                    direction = Sort.Direction.ASC
            ) Pageable pageable
    ) {

        var response = sessaoVotacaoService.listarSessoesVotacao(pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
