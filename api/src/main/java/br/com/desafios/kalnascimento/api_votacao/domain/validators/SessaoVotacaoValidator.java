package br.com.desafios.kalnascimento.api_votacao.domain.validators;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.desafios.kalnascimento.api_votacao.domain.enums.SessaoVotacaoStatusEnum;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.infra.handlers.exceptions.RegraNegocioException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SessaoVotacaoValidator {

    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    public void sessaoFinalizada(UUID id) {
        var sessao = sessaoVotacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        if (sessao.getStatus().equals(SessaoVotacaoStatusEnum.FINALIZADA)) {
            throw new RegraNegocioException("Sessão já foi finalizada");
        }
    }
}

