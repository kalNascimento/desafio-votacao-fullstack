package br.com.desafios.kalnascimento.api_votacao.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_associado")
public class Associado extends BaseEntity {

    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

}
