package br.com.desafios.kalnascimento.api_votacao.controllers;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private UUID pautaId;

    @BeforeEach
    void clean() {
        sessaoVotacaoRepository.deleteAll();
        pautaRepository.deleteAll();
    }

    @BeforeEach
    void setup() {
        Pauta pauta = Pauta.builder()
                .nome("Pauta teste")
                .descricao("Pauta descrição")
                .build();

        pautaRepository.save(pauta);

        pautaId = pauta.getId();
    }

    @Test
    void deveCriarSessaoComSucesso() throws Exception {

        CriarSessaoVotacaoRequestDto request = CriarSessaoVotacaoRequestDto.builder()
                .idPauta(pautaId)
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
                .idPauta(pautaId)
                .build();

        mockMvc.perform(post("/sessao-votacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    @Test
    void deveListarSessoesVotacaoComSucesso() throws Exception {

        SessaoVotacao sessao1 = SessaoVotacao.builder()
                .pauta(Pauta.builder()
                        .id(pautaId)
                        .nome("teste")
                        .descricao("teste")
                        .build())
                .status(SessaoVotacaoStatusEnum.EM_ANDAMENTO)
                .dataHoraFinalizacao(LocalDateTime.now().plusMinutes(1))
                .build();


        sessaoVotacaoRepository.save(sessao1);

        mockMvc.perform(get("/sessao-votacao")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].pauta.id").exists());
    }
}
