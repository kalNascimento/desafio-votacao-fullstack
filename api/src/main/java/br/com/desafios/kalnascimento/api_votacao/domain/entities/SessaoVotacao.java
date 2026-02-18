package br.com.desafios.kalnascimento.api_votacao.domain.entities;

import java.time.LocalDateTime;

import br.com.desafios.kalnascimento.api_votacao.domain.enums.SessaoVotacaoStatusEnum;
import br.com.desafios.kalnascimento.api_votacao.domain.enums.VotoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "tb_sessao_votacao")
public class SessaoVotacao extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "id_pauta", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_pauta"))
    private Pauta pauta;

    @Column(name = "voto_vencedor", length = 20)
    @Enumerated(EnumType.STRING)
    private VotoEnum votoVencedor;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private SessaoVotacaoStatusEnum status;

    @Column(name = "data_hora_finalizacao", nullable = false)
    private LocalDateTime dataHoraFinalizacao;

}
