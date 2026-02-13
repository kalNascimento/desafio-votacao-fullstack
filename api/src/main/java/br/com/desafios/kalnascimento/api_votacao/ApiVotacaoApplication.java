package br.com.desafios.kalnascimento.api_votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ApiVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiVotacaoApplication.class, args);
	}

}
