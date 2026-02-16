package br.com.desafios.kalnascimento.api_votacao.controllers;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarSessaoVotacaoDto;
import br.com.desafios.kalnascimento.api_votacao.domain.entities.Pauta;
import br.com.desafios.kalnascimento.api_votacao.domain.repositories.PautaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SessaoVotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PautaRepository pautaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private UUID pautaId;

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

        CriarSessaoVotacaoDto request = CriarSessaoVotacaoDto.builder()
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

        CriarSessaoVotacaoDto request = CriarSessaoVotacaoDto.builder()
                .idPauta(pautaId)
                .build();

        mockMvc.perform(post("/sessao-votacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyOrNullString())));
    }
}
