package br.com.desafios.kalnascimento.api_votacao.domain.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.SessaoVotacaoResponseDto;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.infra.mappers.SessaoVotacaoMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessaoVotacaoService {

    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    public UUID criarSessaoVotacao(CriarSessaoVotacaoRequestDto dto) {
        var sessaoVotacao = sessaoVotacaoMapper.toEntity(dto);

        if (sessaoVotacao.getDataHoraFinalizacao() == null) {
            sessaoVotacao.setDataHoraFinalizacao(LocalDateTime.now().plusMinutes(1));
        }

        var novaSessaoVotacao = sessaoVotacaoRepository.save(sessaoVotacao);

        return novaSessaoVotacao.getId();
    }

    public Page<SessaoVotacaoResponseDto> listarSessoesVotacao(Pageable pageable) {
        return sessaoVotacaoRepository.findAll(pageable)
                .map(sessaoVotacaoMapper::toDto);
    }
}
