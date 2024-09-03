CREATE TABLE pessoa (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE cliente (
     id BIGSERIAL PRIMARY KEY,
     nome_empresa VARCHAR(150) NOT NULL,
     cpf_responsavel VARCHAR(11) NOT NULL,
     cnpj VARCHAR(14) NOT NULL UNIQUE,
     tipo_plano VARCHAR(10) NOT NULL,
     saldo NUMERIC(10, 2) DEFAULT 0.00,
     limite NUMERIC(10, 2) DEFAULT 0.00,
     consumo NUMERIC(10, 2) DEFAULT 0.00,
     pessoa_id BIGINT NOT NULL UNIQUE,
     CONSTRAINT fk_cliente_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa (id)
);

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(20) NOT NULL,
    pessoa_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_usuario_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa (id)
);

CREATE TABLE mensagem (
    id BIGSERIAL PRIMARY KEY,
    numero_destino VARCHAR(20) NOT NULL,
    is_whatsapp BOOLEAN NOT NULL,
    texto VARCHAR(160) NOT NULL,
    status VARCHAR(10) NOT NULL,
    custo NUMERIC(10, 2),
    cliente_id BIGINT NOT NULL,
    data_envio DATE NOT NULL,
    CONSTRAINT fk_mensagem_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (id)
);
