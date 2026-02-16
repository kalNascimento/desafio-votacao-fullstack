package br.com.desafios.kalnascimento.api_votacao.controllers.dtos;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PautaResponseDto {

    UUID id;

    String nome;

    String descricao;
}
