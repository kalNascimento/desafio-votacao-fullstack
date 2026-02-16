package br.com.desafios.kalnascimento.api_votacao.domain.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarPautaRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.PautaComboDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.PautaRepository;
import br.com.desafios.kalnascimento.api_votacao.infra.mappers.PautaMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    private final PautaMapper pautaMapper;

    public UUID criarPauta(CriarPautaRequestDto dto) {
        var pauta = pautaMapper.toEntity(dto);

        var novaPauta = pautaRepository.save(pauta);

        return novaPauta.getId();
    }

    public List<PautaComboDto> listarPautasCombo() {
        return pautaRepository.findAll().stream()
                .map(pautaMapper::toDto)
                .toList();
    }
}
