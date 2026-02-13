
CREATE TABLE IF NOT EXISTS tb_associado (
    id UUID PRIMARY KEY,
    nome VARCHAR(50),
    cpf VARCHAR(11),
    data_hora_criacao TIMESTAMP NOT NULL
)
