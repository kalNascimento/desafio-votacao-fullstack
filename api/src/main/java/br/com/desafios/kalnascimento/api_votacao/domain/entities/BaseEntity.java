package br.com.desafios.kalnascimento.api_votacao.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.github.f4b6a3.uuid.UuidCreator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @CreatedDate
    @Column(name = "data_hora_criacao", nullable = false)
    private LocalDateTime dataHoraCriacao;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UuidCreator.getTimeOrderedEpoch();
        }
    }
}
