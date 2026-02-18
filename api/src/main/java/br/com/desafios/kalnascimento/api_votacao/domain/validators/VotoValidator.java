package br.com.desafios.kalnascimento.api_votacao.domain.validators;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.VotoRespository;
import br.com.desafios.kalnascimento.api_votacao.infra.handlers.exceptions.RegraNegocioException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VotoValidator {

    private final VotoRespository votoRespository;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    public void associadoJaVotou(UUID idAssociado, UUID idPauta) {
        if (Objects.isNull(idAssociado)) {
            return;
        }

        if (votoRespository.existsByAssociadoIdAndPautaId(idAssociado, idPauta)) {
            throw new RegraNegocioException("Associado já votou");
        }
    }

}
