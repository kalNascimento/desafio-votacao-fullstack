package br.com.desafios.kalnascimento.api_votacao.infra.mappers;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.SessaoVotacao;

@Mapper(componentModel = "spring")
public interface SessaoVotacaoMapper {

    @Mapping(target = "pauta", expression = "java(pautaFromId(dto.idPauta()))")
    @Mapping(target = "status", constant = "EM_ANDAMENTO")
    @Mapping(target = "votoVencedor", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataHoraCriacao", ignore = true)
    @Mapping(target = "version", ignore = true)
    SessaoVotacao toEntity(CriarSessaoVotacaoDto dto);

    default Pauta pautaFromId(UUID id) {
        if (id == null) return null;
        return Pauta.builder().id(id).build();
    }
}
