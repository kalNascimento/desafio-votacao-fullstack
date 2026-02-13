
CREATE TABLE IF NOT EXISTS tb_voto (
    id UUID PRIMARY KEY,
    id_pauta UUID NOT NULL,
    id_associado UUID NOT NULL,
    voto VARCHAR(10) NOT NULL,
    data_hora_criacao TIMESTAMP NOT NULL,
    version INT,
    CONSTRAINT fk_pauta FOREIGN KEY (id_pauta) REFERENCES tb_pauta(id),
    CONSTRAINT fk_associado FOREIGN KEY (id_associado) REFERENCES tb_associado(id),
    CONSTRAINT unique_voto_por_associado_pauta UNIQUE (id_pauta, id_associado)
);
