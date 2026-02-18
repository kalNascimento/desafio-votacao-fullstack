package br.com.desafios.kalnascimento.api_votacao.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafios.kalnascimento.api_votacao.domain.entities.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, UUID> {

    Optional<Associado> findByCpf(String cpf);
}
