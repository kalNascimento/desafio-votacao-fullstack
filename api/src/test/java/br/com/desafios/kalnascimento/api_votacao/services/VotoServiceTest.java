package br.com.desafios.kalnascimento.api_votacao.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.AssociadoRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.VotarRequestDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Associado;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.SessaoVotacao;
import br.com.desafios.kalnascimento.api_votacao.domain.enums.VotoEnum;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.AssociadoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.VotoRespository;
import br.com.desafios.kalnascimento.api_votacao.domain.services.VotoService;
import br.com.desafios.kalnascimento.api_votacao.domain.validators.SessaoVotacaoValidator;
import br.com.desafios.kalnascimento.api_votacao.domain.validators.VotoValidator;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {

    @Mock
    private VotoValidator votoValidator;

    @Mock
    private SessaoVotacaoValidator sessaoVotacaoValidator;

    @Mock
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Mock
    private VotoRespository votoRespository;

    @Mock
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private VotoService service;

    private final static String CPF = "12345678912";
    private final static UUID ID_SESSAO = UUID.fromString("202ea5f2-52b5-4cdc-a66a-bd98a0e4bdaf");
    private final static UUID ID_PAUTA = UUID.fromString("7193c376-4121-4904-945f-49f7a36bba7b");
    private final static UUID ID_ASSOCIADO = UUID.fromString("202ea5f2-52b5-4904-945f-49f7a36bba7b");

    @Test
    void deveVotarComAssociadoExistente() {
        var pauta = Pauta.builder()
                .id(ID_PAUTA)
                .build();

        var sessao = SessaoVotacao.builder()
                .id(ID_SESSAO)
                .pauta(pauta)
                .build();

        var dto = VotarRequestDto.builder()
                .voto(VotoEnum.ACEITAR)
                .idSessao(ID_SESSAO)
                .associado(AssociadoRequestDto.builder()
                        .nome("Teste")
                        .cpf(CPF)
                        .build())
                .build();


        var associado = Associado.builder()
                .id(ID_ASSOCIADO)
                .cpf(CPF)
                .nome("Kalebe")
                .build();

        when(sessaoVotacaoRepository.findById(any())).thenReturn(Optional.of(sessao));
        when(associadoRepository.findByCpf(any())).thenReturn(Optional.of(associado));

        service.votar(dto);

        verify(sessaoVotacaoValidator).sessaoFinalizada(ID_SESSAO);
        verify(votoValidator).associadoJaVotou(ID_ASSOCIADO, ID_PAUTA);
        verify(votoRespository).save(any());
        verify(associadoRepository, never()).save(any());
    }

    @Test
    void deveCriarAssociadoSeNaoExistir() {
        var pauta = Pauta.builder()
                .id(VotoServiceTest.ID_PAUTA)
                .build();

        var sessao = SessaoVotacao.builder()
                .id(ID_SESSAO)
                .pauta(pauta)
                .build();

        var dto = VotarRequestDto.builder()
                .voto(VotoEnum.ACEITAR)
                .idSessao(ID_SESSAO)
                .associado(AssociadoRequestDto.builder()
                        .nome("Teste")
                        .cpf(CPF)
                        .build())
                .build();

        when(sessaoVotacaoRepository.findById(ID_SESSAO)).thenReturn(Optional.of(sessao));
        when(associadoRepository.findByCpf(CPF)).thenReturn(Optional.empty());

        service.votar(dto);

        verify(associadoRepository).save(any());
        verify(votoRespository).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoSessaoNaoEncontrada() {
        var dto = VotarRequestDto.builder()
                .voto(VotoEnum.ACEITAR)
                .idSessao(ID_SESSAO)
                .associado(AssociadoRequestDto.builder()
                        .nome("Teste")
                        .cpf(CPF)
                        .build())
                .build();

        when(sessaoVotacaoRepository.findById(ID_SESSAO)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.votar(dto));

        verify(votoRespository, never()).save(any());
    }
}
