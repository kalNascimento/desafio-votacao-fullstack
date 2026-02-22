package br.com.desafios.kalnascimento.api_votacao.domain.validators;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.desafios.kalnascimento.api_votacao.domain.entities.SessaoVotacao;
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

    public void sessaoFinalizada(SessaoVotacao sessao) {
        if (sessao.getStatus().equals(SessaoVotacaoStatusEnum.FINALIZADA)) {
            throw new RegraNegocioException("Sessão já foi finalizada");
        }
    }

    public void sessaoPodeFinalizar(SessaoVotacao sessao) {
        if (sessao.getDataHoraFinalizacao().isAfter(LocalDateTime.now())) {
            throw new RegraNegocioException(
                    "Não é possível finalizar a sessão antes do horário de expiração"
            );
        }
    }
}

