package br.com.desafios.kalnascimento.api_votacao.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Voto;
import br.com.desafios.kalnascimento.api_votacao.domain.enums.VotoEnum;

public interface VotoRespository extends JpaRepository<Voto, UUID> {

    boolean existsByAssociadoIdAndPautaId(UUID idAssociado, UUID idPauta);

    long countByPautaAndVoto(Pauta pauta, VotoEnum voto);
}
