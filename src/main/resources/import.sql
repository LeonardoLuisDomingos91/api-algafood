insert into cozinha (id, nome) values (1,'Tailandesa');
insert into cozinha (id, nome) values (2,'Indiana');

insert into forma_pagamento(id, descricao) values (1, 'cartão');

insert into restaurante (id,nome, taxa_frete, cozinha_id) values (2,'Thai Gourmet', 10, 1);
insert into restaurante (id,nome, taxa_frete, cozinha_id) values (3,'Thai Delivery', 9.50, 1);
insert into restaurante (id,nome, taxa_frete, cozinha_id) values (4,'Tuk Tuk Comida Indiana', 15, 2);

insert into estado (id,nome) values (1,'São Paulo');

insert into cidade (id,nome, estado_id) values (1,'Araraquara', 1);
insert into cidade (id,nome, estado_id) values (2,'Américo Brasiliense', 1);