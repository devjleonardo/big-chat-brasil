DELETE FROM mensagem;
DELETE FROM usuario;
DELETE FROM cliente;
DELETE FROM pessoa;

ALTER SEQUENCE mensagem_id_seq RESTART WITH 1;
ALTER SEQUENCE usuario_id_seq RESTART WITH 1;
ALTER SEQUENCE cliente_id_seq RESTART WITH 1;
ALTER SEQUENCE pessoa_id_seq RESTART WITH 1;

INSERT INTO pessoa (nome, telefone) VALUES
    ('Maiara Furtado', '84981603036'),
    ('Thayline Pina', '21988772117'),
    ('Sandro Esteves', '44976521674'),
    ('Elimar Gomes', '11997612819');

INSERT INTO cliente (nome_empresa, cpf_responsavel, cnpj, tipo_plano, saldo, limite, consumo, pessoa_id) VALUES
    (
        'Ferragens Mesquita', '96712142075', '31800894000128', 'PRE_PAGO', 50.00, 0.00, 0.00, 
        (SELECT id FROM pessoa WHERE nome = 'Maiara Furtado')
    ),
    (
        'Laboratório Serra', '99451090061', '37545691000119', 'POS_PAGO', 0.00, 200.00, 0.00,
        (SELECT id FROM pessoa WHERE nome = 'Thayline Pina')
    ),
    (
        'Consultoria Muniz', '03948930007', '65025243000120', 'PRE_PAGO', 50.00, 0.00, 0.00,
        (SELECT id FROM pessoa WHERE nome = 'Sandro Esteves')
    ),
    (
        'Propaganda Gomes', '05708041080', '17056180000126', 'POS_PAGO', 0.00, 200.00, 0.00,
        (SELECT id FROM pessoa WHERE nome = 'Elimar Gomes')
    );

INSERT INTO pessoa (nome, telefone) VALUES
    ('Thomas Luiz', '44996871752'),
    ('Letícia Alana', '44995232325');

INSERT INTO usuario (email, senha, perfil, pessoa_id) VALUES
    (
        'maiara@gmail.com', '$2a$12$hbeVWIZS83av2sBPb25LF.yHlqVJxn3GBfXTV91Zboh1ZwRpJ.GgO', 'CLIENTE',
        (SELECT id FROM pessoa WHERE nome = 'Maiara Furtado')
    ),
    (
        'thayline@gmail.com', '$2a$12$hbeVWIZS83av2sBPb25LF.yHlqVJxn3GBfXTV91Zboh1ZwRpJ.GgO', 'CLIENTE',
        (SELECT id FROM pessoa WHERE nome = 'Thayline Pina')
    ),
    (
        'sandro@gmail.com', '$2a$12$hbeVWIZS83av2sBPb25LF.yHlqVJxn3GBfXTV91Zboh1ZwRpJ.GgO', 'CLIENTE',
        (SELECT id FROM pessoa WHERE nome = 'Sandro Esteves')
    ),
    (
        'elimar@gmail.com', '$2a$12$hbeVWIZS83av2sBPb25LF.yHlqVJxn3GBfXTV91Zboh1ZwRpJ.GgO', 'CLIENTE',
        (SELECT id FROM pessoa WHERE nome = 'Elimar Gomes')
    );

INSERT INTO usuario (email, senha, perfil, pessoa_id) VALUES
    (
        'thomas@financeiro.com', '$2a$12$hbeVWIZS83av2sBPb25LF.yHlqVJxn3GBfXTV91Zboh1ZwRpJ.GgO', 'FINANCEIRO',
        (SELECT id FROM pessoa WHERE nome = 'Thomas Luiz')
    ),
    (
        'leticia@financeiro.com', '$2a$12$hbeVWIZS83av2sBPb25LF.yHlqVJxn3GBfXTV91Zboh1ZwRpJ.GgO', 'FINANCEIRO',
        (SELECT id FROM pessoa WHERE nome = 'Letícia Alana')
    );

INSERT INTO mensagem (numero_destino, is_whatsapp, texto, status, custo, cliente_id, data_envio) VALUES
    (
     '84997304298', true, 'Promoção exclusiva de ferramentas! Confira na Ferragens Mesquita.', 'ENVIADA', 0.25,
     (SELECT id FROM cliente WHERE nome_empresa = 'Ferragens Mesquita'), '2024-09-02'
    ),
    (
     '21982837806', false, 'Resultado dos exames disponíveis. Acesse o site do Laboratório Serra.', 'ENVIADA', 0.25,
     (SELECT id FROM cliente WHERE nome_empresa = 'Laboratório Serra'), '2024-09-02'
    ),
    (
     '44982637675', true, 'Consultoria Muniz: Agende sua sessão de planejamento estratégico.', 'ENVIADA', 0.25,
     (SELECT id FROM cliente WHERE nome_empresa = 'Consultoria Muniz'), '2024-09-02'
    ),
    (
     '11992700804', false, 'Novas campanhas publicitárias da Propaganda Gomes. Entre em contato!', 'ENVIADA', 0.25,
     (SELECT id FROM cliente WHERE nome_empresa = 'Propaganda Gomes'), '2024-09-02'
    );

