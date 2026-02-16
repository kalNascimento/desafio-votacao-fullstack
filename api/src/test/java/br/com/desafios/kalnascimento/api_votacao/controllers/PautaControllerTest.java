package br.com.desafios.kalnascimento.api_votacao.controllers;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.desafios.kalnascimento.api_votacao.controllers.dtos.CriarPautaRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveCriarPautaComSucesso() throws Exception {

        CriarPautaRequestDto request = CriarPautaRequestDto.builder()
                .nome("Nova pauta teste")
                .descricao("Descrição da pauta")
                .build();

        mockMvc.perform(post("/pauta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    @Test
    void deveRetornar400QuandoTituloNaoInformado() throws Exception {

        CriarPautaRequestDto request = CriarPautaRequestDto.builder()
                .nome(null)
                .descricao("Descrição")
                .build();

        mockMvc.perform(post("/pauta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

