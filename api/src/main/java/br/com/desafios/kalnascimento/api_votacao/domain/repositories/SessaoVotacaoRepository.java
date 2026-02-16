package br.com.desafios.kalnascimento.api_votacao.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafios.kalnascimento.api_votacao.domain.entities.SessaoVotacao;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, UUID> {
}
