package br.com.desafios.kalnascimento.api_votacao.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.PautaResponseDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.SessaoVotacaoResponseDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.SessaoVotacao;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.services.SessaoVotacaoService;
import br.com.desafios.kalnascimento.api_votacao.infra.mappers.SessaoVotacaoMapper;

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

        CriarSessaoVotacaoRequestDto dto = CriarSessaoVotacaoRequestDto.builder()
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

        CriarSessaoVotacaoRequestDto dto = CriarSessaoVotacaoRequestDto.builder()
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

        CriarSessaoVotacaoRequestDto dto = CriarSessaoVotacaoRequestDto.builder()
                .idPauta(UUID.randomUUID())
                .dataHoraFinalizacao(LocalDateTime.now())
                .build();

        when(sessaoVotacaoMapper.toEntity(dto)).thenReturn(sessao);
        when(sessaoVotacaoRepository.save(sessao)).thenReturn(sessao);

        UUID resultado = service.criarSessaoVotacao(dto);

        assertEquals(idEsperado, resultado);
    }

    @Test
    void deveListarSessoesVotacaoComSucesso() {

        Pageable pageable = PageRequest.of(0, 10);

        SessaoVotacao sessao1 = SessaoVotacao.builder()
                .id(UUID.randomUUID())
                .dataHoraFinalizacao(LocalDateTime.now().plusMinutes(1))
                .build();

        SessaoVotacao sessao2 = SessaoVotacao.builder()
                .id(UUID.randomUUID())
                .dataHoraFinalizacao(LocalDateTime.now().plusMinutes(2))
                .build();

        List<SessaoVotacao> sessoes = List.of(sessao1, sessao2);
        Page<SessaoVotacao> page = new PageImpl<>(sessoes, pageable, sessoes.size());

        SessaoVotacaoResponseDto dto1 = SessaoVotacaoResponseDto.builder()
                .pauta(PautaResponseDto.builder()
                        .id(UUID.randomUUID())
                        .nome("teste")
                        .descricao("teste")
                        .build())
                .dataHoraFinalizacao(sessao1.getDataHoraFinalizacao())
                .build();

        SessaoVotacaoResponseDto dto2 = SessaoVotacaoResponseDto.builder()
                .pauta(PautaResponseDto.builder()
                        .id(UUID.randomUUID())
                        .nome("teste")
                        .descricao("teste")
                        .build())
                .dataHoraFinalizacao(sessao2.getDataHoraFinalizacao())
                .build();

        when(sessaoVotacaoRepository.findAll(pageable)).thenReturn(page);
        when(sessaoVotacaoMapper.toDto(sessao1)).thenReturn(dto1);
        when(sessaoVotacaoMapper.toDto(sessao2)).thenReturn(dto2);

        Page<SessaoVotacaoResponseDto> resultado = service.listarSessoesVotacao(pageable);

        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals(dto1, resultado.getContent().get(0));
        assertEquals(dto2, resultado.getContent().get(1));

        verify(sessaoVotacaoRepository).findAll(pageable);
        verify(sessaoVotacaoMapper).toDto(sessao1);
        verify(sessaoVotacaoMapper).toDto(sessao2);
    }
}
