package br.com.desafios.kalnascimento.api_votacao.infra.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarPautaRequestDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataHoraCriacao", ignore = true)
    @Mapping(target = "version", ignore = true)
    Pauta toEntity(CriarPautaRequestDto pauta);
}
