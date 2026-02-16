package br.com.desafios.kalnascimento.api_votacao;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableJpaAuditing
@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class ApiVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiVotacaoApplication.class, args);
	}

}
