
CREATE TABLE IF NOT EXISTS tb_sessao_votacao (
    id UUID PRIMARY KEY,
    id_pauta UUID UNIQUE NOT NULL,
    voto_vencedor VARCHAR(10),
    data_hora_criacao TIMESTAMP NOT NULL,
    data_hora_finalizacao TIMESTAMP NOT NULL,
    status VARCHAR(15),
    version INT NOT NULL,
    CONSTRAINT fk_pauta
        FOREIGN KEY (id_pauta) REFERENCES tb_pauta(id)
);
