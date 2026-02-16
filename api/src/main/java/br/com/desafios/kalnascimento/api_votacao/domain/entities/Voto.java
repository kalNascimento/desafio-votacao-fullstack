package br.com.desafios.kalnascimento.api_votacao.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@Table(name = "tb_voto")
public class Voto extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_pauta",
            nullable = false
    )
    private Pauta pauta;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_associado",
            nullable = false
    )
    private Associado associado;

    @NotBlank
    @Size(max = 10)
    @Column(name = "voto", nullable = false, length = 10)
    private String voto;

}

