package br.com.desafios.kalnascimento.api_votacao.services;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.SessaoVotacao;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.services.SessaoVotacaoService;
import br.com.desafios.kalnascimento.api_votacao.infra.mappers.SessaoVotacaoMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoServiceTest {

    @Mock
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Mock
    private SessaoVotacaoMapper sessaoVotacaoMapper;

    @InjectMocks
    private SessaoVotacaoService service;

    private SessaoVotacao sessao;

    @BeforeEach
    void setup() {
        sessao = new SessaoVotacao();
        sessao.setId(UUID.randomUUID());
    }

    @Test
    void deveSalvarSessaoQuandoDataInformada() {

        LocalDateTime data = LocalDateTime.now().plusMinutes(5);

        CriarSessaoVotacaoDto dto = CriarSessaoVotacaoDto.builder()
                .idPauta(UUID.randomUUID())
                .dataHoraFinalizacao(data)
                .build();

        when(sessaoVotacaoMapper.toEntity(dto)).thenReturn(sessao);
        sessao.setDataHoraFinalizacao(data);

        when(sessaoVotacaoRepository.save(sessao)).thenReturn(sessao);

        UUID resultado = service.criarSessaoVotacao(dto);

        assertEquals(sessao.getId(), resultado);
        verify(sessaoVotacaoRepository).save(sessao);
    }

    @Test
    void deveDefinirDataPadraoQuandoNaoInformada() {

        CriarSessaoVotacaoDto dto = CriarSessaoVotacaoDto.builder()
                .idPauta(UUID.randomUUID())
                .dataHoraFinalizacao(null)
                .build();

        when(sessaoVotacaoMapper.toEntity(dto)).thenReturn(sessao);

        when(sessaoVotacaoRepository.save(sessao)).thenReturn(sessao);

        UUID resultado = service.criarSessaoVotacao(dto);

        assertNotNull(sessao.getDataHoraFinalizacao());
        assertTrue(sessao.getDataHoraFinalizacao().isAfter(LocalDateTime.now().minusSeconds(5)));

        verify(sessaoVotacaoRepository).save(sessao);
        assertNotNull(resultado);
    }

    @Test
    void deveRetornarUuidSalvo() {

        UUID idEsperado = UUID.randomUUID();
        sessao.setId(idEsperado);

        CriarSessaoVotacaoDto dto = CriarSessaoVotacaoDto.builder()
                .idPauta(UUID.randomUUID())
                .dataHoraFinalizacao(LocalDateTime.now())
                .build();

        when(sessaoVotacaoMapper.toEntity(dto)).thenReturn(sessao);
        when(sessaoVotacaoRepository.save(sessao)).thenReturn(sessao);

        UUID resultado = service.criarSessaoVotacao(dto);

        assertEquals(idEsperado, resultado);
    }
}
