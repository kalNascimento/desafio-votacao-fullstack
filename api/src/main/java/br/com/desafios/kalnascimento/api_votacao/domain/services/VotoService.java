package br.com.desafios.kalnascimento.api_votacao.domain.services;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.VotarRequestDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Associado;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Voto;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.AssociadoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.VotoRespository;
import br.com.desafios.kalnascimento.api_votacao.domain.validators.SessaoVotacaoValidator;
import br.com.desafios.kalnascimento.api_votacao.domain.validators.VotoValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VotoService {

    private final VotoRespository votoRespository;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;
    private final AssociadoRepository associadoRepository;

    private final VotoValidator votoValidator;
    private final SessaoVotacaoValidator sessaoVotacaoValidator;

    public void votar(VotarRequestDto dto) {
        var sessao = sessaoVotacaoRepository.findById(dto.idSessao())
                .orElseThrow(() -> new EntityNotFoundException("Sessao votação não encontrada"));

        sessaoVotacaoValidator.sessaoFinalizada(sessao.getId());

        var associado = associadoRepository.findByCpf(dto.associado().cpf())
                .orElse(Associado.builder()
                        .nome(dto.associado().nome())
                        .cpf(dto.associado().cpf())
                        .build());

        votoValidator.associadoJaVotou(associado.getId(), sessao.getPauta().getId());

        if (Objects.isNull(associado.getId())) {
            associadoRepository.save(associado);
        }

        var voto = Voto.builder()
                .pauta(sessao.getPauta())
                .voto(dto.voto())
                .associado(associado)
                .build();

        votoRespository.save(voto);
    }
}
