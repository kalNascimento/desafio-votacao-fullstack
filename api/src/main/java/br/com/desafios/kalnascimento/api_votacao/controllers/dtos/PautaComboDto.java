package br.com.desafios.kalnascimento.api_votacao.controllers.dtos;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PautaComboDto {

    UUID id;

    String nome;
}
