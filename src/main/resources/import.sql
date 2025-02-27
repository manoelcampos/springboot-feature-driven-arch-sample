insert into estado(descricao, sigla) values ('São Paulo', 'SP');
insert into estado(descricao, sigla) values ('Paraná', 'PR');
insert into estado(descricao, sigla) values ('Santa Catarina', 'SC');
insert into estado(descricao, sigla) values ('Rio Grande do Sul', 'RS');
insert into estado(descricao, sigla) values ('Mato Grosso do Sul', 'MS');
insert into estado(descricao, sigla) values ('Rondônia', 'RO');
insert into estado(descricao, sigla) values ('Acre', 'AC');
insert into estado(descricao, sigla) values ('Amazonas', 'AM');
insert into estado(descricao, sigla) values ('Roraima', 'RR');
insert into estado(descricao, sigla) values ('Pará', 'PA');
insert into estado(descricao, sigla) values ('Amapá', 'AP');
insert into estado(descricao, sigla) values ('Tocantins', 'TO');
insert into estado(descricao, sigla) values ('Maranhão', 'MA');
insert into estado(descricao, sigla) values ('Rio Grande do Norte', 'RN');
insert into estado(descricao, sigla) values ('Paraíba', 'PB');
insert into estado(descricao, sigla) values ( 'Pernambuco', 'PE');
insert into estado(descricao, sigla) values ( 'Alagoas', 'AL');
insert into estado(descricao, sigla) values ( 'Sergipe', 'SE');
insert into estado(descricao, sigla) values ( 'Bahia', 'BA');
insert into estado(descricao, sigla) values ( 'Minas Gerais', 'MG');
insert into estado(descricao, sigla) values ( 'Rio de Janeiro', 'RJ');
insert into estado(descricao, sigla) values ( 'Mato Grosso', 'MT');
insert into estado(descricao, sigla) values ( 'Goiás', 'GO');
insert into estado(descricao, sigla) values ( 'Distrito Federal', 'DF');
insert into estado(descricao, sigla) values ( 'Piauí', 'PI');
insert into estado(descricao, sigla) values ( 'Ceará', 'CE');
insert into estado(descricao, sigla) values ( 'Espírito Santo', 'ES');

-- Capitais de cada Estado
insert into cidade (estado_id, descricao) values (1,'São Paulo'); -- São Paulo
insert into cidade (estado_id, descricao) values (2, 'Curitiba'); -- Paraná
insert into cidade (estado_id, descricao) values (3,'Florianópolis'); -- Santa Catarina
insert into cidade (estado_id, descricao) values (4,'Porto Alegre'); -- Rio Grande do Sul
insert into cidade (estado_id, descricao) values (5,'Campo Grande'); -- Mato Grosso do Sul
insert into cidade (estado_id, descricao) values (6, 'Porto Velho'); -- Rondônia
insert into cidade (estado_id, descricao) values (7, 'Rio Branco'); -- Acre
insert into cidade (estado_id, descricao) values (8, 'Manaus'); -- Amazonas
insert into cidade (estado_id, descricao) values (9, 'Boa Vista'); -- Roraima
insert into cidade (estado_id, descricao) values (10, 'Belém'); -- Pará
insert into cidade (estado_id, descricao) values (11, 'Macapá'); -- Amapá
insert into cidade (estado_id, descricao) values (12, 'Palmas'); -- Tocantins
insert into cidade (estado_id, descricao) values (13,'São Luís'); -- Maranhão
insert into cidade (estado_id, descricao) values (14,'Natal'); -- Rio Grande do Norte
insert into cidade (estado_id, descricao) values (15,'João Pessoa'); -- Paraíba
insert into cidade (estado_id, descricao) values (16,'Recife'); -- Pernambuco
insert into cidade (estado_id, descricao) values (17, 'Maceió'); -- Alagoas
insert into cidade (estado_id, descricao) values (18, 'Aracaju'); -- Sergipe
insert into cidade (estado_id, descricao) values (19, 'Salvador'); -- Bahia
insert into cidade (estado_id, descricao) values (20,'Belo Horizonte'); -- Minas Gerais
insert into cidade (estado_id, descricao) values (21,'Rio de Janeiro'); -- Rio de Janeiro
insert into cidade (estado_id, descricao) values (22, 'Cuiabá'); -- Mato Grosso
insert into cidade (estado_id, descricao) values (23,'Goiânia'); -- Goiás
insert into cidade (estado_id, descricao) values (24,'Brasília'); -- Distrito Federal
insert into cidade (estado_id, descricao) values (25, 'Teresina'); -- Piauí
insert into cidade (estado_id, descricao) values (26, 'Fortaleza'); -- Ceará
insert into cidade (estado_id, descricao) values (27,'Vitória'); -- Espírito Santo

-- CPFs válidos gerados em https://www.4devs.com.br/gerador_de_cpf
insert into cliente(nome, cpf, cidade_id) values ('Manoel Campos', '33184755053', 4);
insert into cliente(nome, cpf, cidade_id) values ('João Pedro', '28327907042', 1);
insert into cliente(nome, cpf, cidade_id) values ('Ana Paula Maria', '02894896018', 2);
insert into cliente(nome, cpf, cidade_id) values ('Maria Francisca', '63512889085', 6);
insert into cliente(nome, cpf, cidade_id) values ('Pedro Miguel', '94407622091', 6);
insert into cliente(nome, cpf, cidade_id) values ('Pedro Artur', '33660586099', 6);
insert into cliente(nome, cpf, cidade_id) values ('Paula Gomes', '79538783050', 3);
insert into cliente(nome, cpf, cidade_id) values ('Helena Silva', '32253097020', 4);
insert into cliente(nome, cpf, cidade_id) values ('Marta Silva', '26341362005', 1);

insert into produto(descricao, preco, estoque) values ('Notebook', 5.00, 2);
insert into produto(descricao, preco, estoque) values ('TV', 1500.00, 10);
insert into produto(descricao, preco, estoque) values ('iPhone', 4000.00, 100);
insert into produto(descricao, preco, estoque) values ('Teclado', 200.00, 50);

-- Produto sem estoque
insert into produto(descricao, preco, estoque) values ('Mouse', 150.00, 0);

insert into venda (cliente_id, data_hora) values (1, '2025-02-23');
    insert into item_venda (venda_id, produto_id, quant) values (1, 1, 2);
    insert into item_venda (venda_id, produto_id, quant) values (1, 3, 4);
    insert into item_venda (venda_id, produto_id, quant) values (1, 4, 1);

insert into venda (cliente_id, data_hora) values (1, '2024-10-30');
    insert into item_venda (venda_id, produto_id, quant) values (2, 2, 2);
    insert into item_venda (venda_id, produto_id, quant) values (2, 5, 1);

insert into venda (cliente_id, data_hora) values (3, '2025-01-12');
    insert into item_venda (venda_id, produto_id, quant) values (3, 4, 1);

-- Venda para produto sem estoque, para simular que a venda foi realizada antes e agora não tem mais estoque
insert into venda (cliente_id, data_hora) values (2, '2025-02-24');
    insert into item_venda (venda_id, produto_id, quant) values (4, 5, 4);
