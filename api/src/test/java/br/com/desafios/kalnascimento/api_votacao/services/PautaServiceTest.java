package br.com.desafios.kalnascimento.api_votacao.services;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarPautaRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.PautaComboResponseDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.PautaRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.services.PautaService;
import br.com.desafios.kalnascimento.api_votacao.infra.mappers.PautaMapper;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaMapper pautaMapper;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @Test
    void deveCriarPautaComSucesso() {
        var idEsperado = UUID.randomUUID();

        var dto = CriarPautaRequestDto.builder()
                .nome("Teste nome")
                .descricao("Teste descricao")
                .build();

        var pauta = Pauta.builder()
                .nome("Teste nome")
                .descricao("Teste descricao")
                .build();

        var pautaSalva = Pauta.builder()
                .id(idEsperado)
                .nome(dto.nome())
                .descricao(dto.descricao())
                .build();

        when(pautaMapper.toEntity(dto)).thenReturn(pauta);
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaSalva);

        var resultado = pautaService.criarPauta(dto);

        assertEquals(idEsperado, resultado);
        verify(pautaRepository).save(any(Pauta.class));
    }

    @Test
    void deveMapearCamposCorretamenteAoSalvar() {
        var dto = CriarPautaRequestDto.builder()
                .nome("Teste nome")
                .descricao("Teste descricao")
                .build();

        var pauta = Pauta.builder()
                .nome("Teste nome")
                .descricao("Teste descricao")
                .build();

        var pautaSalva = Pauta.builder()
                .id(UUID.randomUUID())
                .nome(dto.nome())
                .descricao(dto.descricao())
                .build();

        ArgumentCaptor<Pauta> captor = ArgumentCaptor.forClass(Pauta.class);

        when(pautaMapper.toEntity(dto)).thenReturn(pauta);
        when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaSalva);

        pautaService.criarPauta(dto);

        verify(pautaRepository).save(captor.capture());

        var pautaCapturada = captor.getValue();

        assertEquals(dto.nome(), pautaCapturada.getNome());
        assertEquals(dto.descricao(), pautaCapturada.getDescricao());
    }

    @Test
    void deveListarPautasCombo() {

        Pauta pauta1 = Pauta.builder()
                .id(UUID.randomUUID())
                .nome("Pauta 1")
                .build();

        Pauta pauta2 = Pauta.builder()
                .id(UUID.randomUUID())
                .nome("Pauta 2")
                .build();

        List<Pauta> pautas = List.of(pauta1, pauta2);

        PautaComboResponseDto dto1 = PautaComboResponseDto.builder()
                .id(pauta1.getId())
                .nome("Pauta 1")
                .build();

        PautaComboResponseDto dto2 = PautaComboResponseDto.builder()
                .id(pauta2.getId())
                .nome("Pauta 2")
                .build();

        when(pautaRepository.findAll()).thenReturn(pautas);
        when(pautaMapper.toDto(pauta1)).thenReturn(dto1);
        when(pautaMapper.toDto(pauta2)).thenReturn(dto2);

        List<PautaComboResponseDto> resultado = pautaService.listarPautasCombo();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(dto1, resultado.get(0));
        assertEquals(dto2, resultado.get(1));

        verify(pautaRepository).findAll();
        verify(pautaMapper).toDto(pauta1);
        verify(pautaMapper).toDto(pauta2);
    }
}
