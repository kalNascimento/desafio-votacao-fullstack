
CREATE TABLE IF NOT EXISTS tb_associado (
    id UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_hora_criacao TIMESTAMP NOT NULL,
    version INT NOT NULL
)
