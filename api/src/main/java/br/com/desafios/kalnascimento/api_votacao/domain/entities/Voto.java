package br.com.desafios.kalnascimento.api_votacao.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_voto")
public class Voto extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_pauta",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_pauta")
    )
    private Pauta pauta;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_associado",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_associado")
    )
    private Associado associado;

    @NotBlank
    @Size(max = 10)
    @Column(name = "voto", nullable = false, length = 10)
    private String voto;

}

