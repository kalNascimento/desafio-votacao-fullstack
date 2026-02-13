
CREATE TABLE IF NOT EXISTS tb_pauta (
    id UUID PRIMARY KEY,
    nome VARCHAR(50),
    descricao VARCHAR(150),
    data_hora_criacao TIMESTAMP NOT NULL
)
