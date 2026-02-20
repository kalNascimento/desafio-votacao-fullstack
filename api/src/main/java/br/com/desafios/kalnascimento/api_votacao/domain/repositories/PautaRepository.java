package br.com.desafios.kalnascimento.api_votacao.domain.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, UUID> {

    List<Pauta> findBySessaoVotacaoIsNull();
}
