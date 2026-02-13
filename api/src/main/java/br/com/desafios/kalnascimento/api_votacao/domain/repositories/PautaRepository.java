package br.com.desafios.kalnascimento.api_votacao.domain.repositories;

import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PautaRepository extends JpaRepository<Pauta, UUID> {
}
