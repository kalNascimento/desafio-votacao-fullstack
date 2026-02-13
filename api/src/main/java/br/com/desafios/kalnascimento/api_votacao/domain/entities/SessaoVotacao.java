package br.com.desafios.kalnascimento.api_votacao.domain.entities;

import br.com.desafios.kalnascimento.api_votacao.domain.enums.SessaoVotacaoStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_sessao_votacao")
public class SessaoVotacao extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "id_pauta", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_pauta"))
    private Pauta pauta;

    @Column(name = "voto_vencedor", length = 10)
    private String votoVencedor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SessaoVotacaoStatusEnum status;

    @Column(name = "data_hora_finalizacao", nullable = false)
    private LocalDateTime dataHoraFinalizacao;

}
