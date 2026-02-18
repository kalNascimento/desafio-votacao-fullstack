package br.com.desafios.kalnascimento.api_votacao.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.AssociadoRequestDto;
import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.VotarRequestDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Associado;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.SessaoVotacao;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Voto;
import br.com.desafios.kalnascimento.api_votacao.domain.enums.SessaoVotacaoStatusEnum;
import br.com.desafios.kalnascimento.api_votacao.domain.enums.VotoEnum;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.AssociadoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.PautaRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.VotoRespository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    private VotoRespository votoRespository;

    @Autowired
    private AssociadoRepository associadoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private SessaoVotacao sessao;

    @BeforeEach
    void clean() {
        votoRespository.deleteAllInBatch();
        sessaoVotacaoRepository.deleteAllInBatch();
        associadoRepository.deleteAllInBatch();
        pautaRepository.deleteAllInBatch();
    }

    @BeforeEach
    void setup() {
        pautaRepository.deleteAll();

        Pauta pauta = Pauta.builder()
                .id(UUID.randomUUID())
                .nome("Pauta teste")
                .descricao("Descricao teste")
                .build();

        var novaPauta = pautaRepository.save(pauta);

        SessaoVotacao sessao = SessaoVotacao.builder()
                .pauta(Pauta.builder()
                        .id(novaPauta.getId())
                        .nome("teste")
                        .descricao("teste")
                        .build())
                .status(SessaoVotacaoStatusEnum.EM_ANDAMENTO)
                .dataHoraFinalizacao(LocalDateTime.now().plusMinutes(1))
                .build();

        this.sessao = sessaoVotacaoRepository.save(sessao);
    }

    @Test
    void deveVotarComSucesso() throws Exception {

        VotarRequestDto request = VotarRequestDto.builder()
                .idSessao(sessao.getId())
                .voto(VotoEnum.ACEITAR)
                .associado(AssociadoRequestDto.builder()
                        .nome("Associado Teste")
                        .cpf("57323237084")
                        .build())
                .build();

        mockMvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveVotarComSucessoComAssociadoExistente() throws Exception {
        var associado = AssociadoRequestDto.builder()
                .nome("Associado Teste")
                .cpf("57323237084")
                .build();

        VotarRequestDto request = VotarRequestDto.builder()
                .idSessao(sessao.getId())
                .voto(VotoEnum.ACEITAR)
                .associado(associado)
                .build();

        var novoAssociado = associadoRepository.save(Associado.builder()
                .cpf(associado.cpf())
                .nome(associado.nome())
                .build());

        associadoRepository.save(novoAssociado);

        mockMvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveResultarEmBadRequest() throws Exception {
        var associado = AssociadoRequestDto.builder()
                .nome("Associado Teste")
                .cpf("57323237084")
                .build();

        VotarRequestDto request = VotarRequestDto.builder()
                .idSessao(sessao.getId())
                .voto(VotoEnum.ACEITAR)
                .associado(associado)
                .build();

        var novoAssociado = associadoRepository.save(Associado.builder()
                .cpf(associado.cpf())
                .nome(associado.nome())
                .build());

        votoRespository.save(Voto.builder()
                .voto(VotoEnum.ACEITAR)
                .pauta(sessao.getPauta())
                .associado(novoAssociado)
                .build());

        mockMvc.perform(post("/voto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
