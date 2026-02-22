package br.com.desafios.kalnascimento.api_votacao.domain.services;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.desafios.kalnascimento.api_votacao.infra.handlers.exceptions.RegraNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.SessaoVotacaoResponseDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.enums.SessaoVotacaoStatusEnum;
import br.com.desafios.kalnascimento.api_votacao.domain.enums.VotoEnum;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.VotoRespository;
import br.com.desafios.kalnascimento.api_votacao.domain.validators.SessaoVotacaoValidator;
import br.com.desafios.kalnascimento.api_votacao.infra.mappers.SessaoVotacaoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessaoVotacaoService {

    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final VotoRespository votoRespository;
    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    private final SessaoVotacaoValidator sessaoVotacaoValidator;

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

    public void finalizarSessaoVotacao(UUID id) {
        var sessao = sessaoVotacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessao votação não encontrada"));

        sessaoVotacaoValidator.sessaoFinalizada(sessao);
        sessaoVotacaoValidator.sessaoPodeFinalizar(sessao);

        sessao.setStatus(SessaoVotacaoStatusEnum.FINALIZADA);
        sessao.setVotoVencedor(getVotoVencedor(sessao.getPauta()));

        sessaoVotacaoRepository.save(sessao);
    }

    public SessaoVotacaoResponseDto obterSessaoVotacao(UUID id) {
        var sessao = sessaoVotacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        if (!sessao.getDataHoraFinalizacao().isAfter(LocalDateTime.now())) {
            sessao.setStatus(SessaoVotacaoStatusEnum.FINALIZADA);
            sessao.setVotoVencedor(getVotoVencedor(sessao.getPauta()));

            sessaoVotacaoRepository.save(sessao);
        }

        return  sessaoVotacaoMapper.toDto(sessao);
    }

    private VotoEnum getVotoVencedor(Pauta pauta) {
        long aceitar = votoRespository.countByPautaAndVoto(pauta, VotoEnum.ACEITAR);
        long declinar = votoRespository.countByPautaAndVoto(pauta, VotoEnum.DECLINAR);

        return aceitar > declinar ? VotoEnum.ACEITAR : VotoEnum.DECLINAR;
    }
}
