package br.com.desafios.kalnascimento.api_votacao.domain.services;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarPautaRequestDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    public UUID criarPauta(CriarPautaRequestDto dto) {
        var pauta = Pauta.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .build();

        var novaPauta = pautaRepository.save(pauta);

        return novaPauta.getId();
    }
}
