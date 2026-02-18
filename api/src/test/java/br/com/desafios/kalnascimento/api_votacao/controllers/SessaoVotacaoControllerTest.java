package br.com.desafios.kalnascimento.api_votacao.controllers;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoRequestDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.SessaoVotacao;
import br.com.desafios.kalnascimento.api_votacao.domain.enums.SessaoVotacaoStatusEnum;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.PautaRepository;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.SessaoVotacaoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SessaoVotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private Pauta pauta;

    @BeforeEach
    void clean() {
        sessaoVotacaoRepository.deleteAllInBatch();
        pautaRepository.deleteAllInBatch();
    }

    @BeforeEach
    void setup() {
        Pauta pautaSalva = Pauta.builder()
                .nome("Pauta teste")
                .descricao("Pauta descrição")
                .build();

        pauta = pautaRepository.save(pautaSalva);
    }

    @Test
    void deveCriarSessaoComSucesso() throws Exception {

        CriarSessaoVotacaoRequestDto request = CriarSessaoVotacaoRequestDto.builder()
                .idPauta(pauta.getId())
                .dataHoraFinalizacao(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/sessao-votacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    @Test
    void deveCriarSessaoComSucessoQuandoDataHoraNaoInformada() throws Exception {

        CriarSessaoVotacaoRequestDto request = CriarSessaoVotacaoRequestDto.builder()
                .idPauta(pauta.getId())
                .build();

        mockMvc.perform(post("/sessao-votacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    @Test
    void deveListarSessoesVotacaoComSucesso() throws Exception {

        SessaoVotacao sesssao = SessaoVotacao.builder()
                .pauta(pauta)
                .status(SessaoVotacaoStatusEnum.EM_ANDAMENTO)
                .dataHoraFinalizacao(LocalDateTime.now().plusMinutes(1))
                .build();

        sessaoVotacaoRepository.save(sesssao);

        mockMvc.perform(get("/sessao-votacao")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].pauta.id").exists());
    }

    @Test
    void deveFinalizarSessaoComSucesso() throws Exception {

        SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
                .pauta(pauta)
                .status(SessaoVotacaoStatusEnum.EM_ANDAMENTO)
                .dataHoraFinalizacao(LocalDateTime.now().plusMinutes(1))
                .build();

        var idSessao = sessaoVotacaoRepository.save(sessaoVotacao).getId();

        mockMvc.perform(patch("/sessao-votacao/finalizar/" + idSessao)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveResultarEmServerError() throws Exception {
        var idSessao = UUID.randomUUID();

        mockMvc.perform(patch("/sessao-votacao/finalizar/" + idSessao)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deveResultarEmBadRequest() throws Exception {
        SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
                .pauta(pauta)
                .status(SessaoVotacaoStatusEnum.FINALIZADA)
                .dataHoraFinalizacao(LocalDateTime.now().plusMinutes(1))
                .build();

        var idSessao = sessaoVotacaoRepository.save(sessaoVotacao).getId();

        mockMvc.perform(patch("/sessao-votacao/finalizar/" + idSessao)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
