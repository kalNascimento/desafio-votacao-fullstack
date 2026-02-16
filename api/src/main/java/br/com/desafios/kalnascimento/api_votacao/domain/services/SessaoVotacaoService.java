package br.com.desafios.kalnascimento.api_votacao.domain.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoDto;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.infra.mappers.SessaoVotacaoMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessaoVotacaoService {

    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    public UUID criarSessaoVotacao(CriarSessaoVotacaoDto dto) {
        var sessaoVotacao = sessaoVotacaoMapper.toEntity(dto);

        if (sessaoVotacao.getDataHoraFinalizacao() == null) {
            sessaoVotacao.setDataHoraFinalizacao(LocalDateTime.now().plusMinutes(1));
        }

        var novaSessaoVotacao = sessaoVotacaoRepository.save(sessaoVotacao);

        return novaSessaoVotacao.getId();
    }
}
