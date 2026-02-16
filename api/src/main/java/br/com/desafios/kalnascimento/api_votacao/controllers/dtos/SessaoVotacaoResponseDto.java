package br.com.desafios.kalnascimento.api_votacao.controllers.dtos;

import java.time.LocalDateTime;

import br.com.desafios.kalnascimento.api_votacao.domain.enums.SessaoVotacaoStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SessaoVotacaoResponseDto {

    private PautaResponseDto pauta;

    private SessaoVotacaoStatusEnum status;

    private LocalDateTime dataHoraFinalizacao;
}
