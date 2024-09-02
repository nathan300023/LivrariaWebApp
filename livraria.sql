CREATE SCHEMA livraria;

CREATE TABLE livraria.cliente(
 cpf VARCHAR(11) NOT NULL,
 pnome varchar(20) NOT NULL,
 snome varchar(80) NOT NULL,
 email varchar(30) NOT NULL,
 endereco varchar(40) NOT NULL,
 CONSTRAINT pk_cliente PRIMARY KEY(cpf),
 CONSTRAINT uk_cliente UNIQUE(email)
);

CREATE SEQUENCE livraria.pedido_num
    START WITH 1 INCREMENT BY 1;
    
CREATE TABLE livraria.pedidos(
num_pedido int DEFAULT nextval('livraria.pedido_num'),
data_pedido date NOT NULL,
total_pedido numeric(10,2) NOT NULL,
cliente_cpf VARCHAR(11),
CONSTRAINT pk_pedidos PRIMARY KEY(num_pedido),
CONSTRAINT fk_pedidos FOREIGN KEY(cliente_cpf) REFERENCES livraria.cliente(cpf)
);

CREATE SEQUENCE livraria.livro_cod
START WITH 1 INCREMENT BY 1;

CREATE TABLE livraria.livros(
cod_livro int default nextval('livraria.livro_cod'),
titulo varchar(50) NOT NULL,
autor varchar(50) NOT NULL,
editora varchar(40) NOT NULL,
preco numeric(10,2) NOT NULL,
estoque int NOT NULL,
CONSTRAINT pk_livros PRIMARY KEY (cod_livro)
);

CREATE SEQUENCE livraria.carrinho_sessao
START WITH 1 INCREMENT BY 1;

CREATE TABLE livraria.carrinho(
data_criacao date NOT NULL,
sessao int DEFAULT nextval('livraria.carrinho_sessao'),
cliente_cpf VARCHAR(11),
num_pedido int,
CONSTRAINT pk_carrinho PRIMARY KEY (sessao,cliente_cpf),
CONSTRAINT fk_carrinho_cliente FOREIGN KEY(cliente_cpf) REFERENCES livraria.cliente(cpf),
CONSTRAINT fk_carrinho_pedido FOREIGN KEY (num_pedido) REFERENCES livraria.pedidos(num_pedido)
);

CREATE TABLE livraria.pedidolivro(
num_pedido int,
cod_livro int,
quantidade int NOT NULL,
CONSTRAINT pk_pedidolivro PRIMARY KEY (num_pedido,cod_livro),
CONSTRAINT fk_pedidolivro FOREIGN KEY(num_pedido) REFERENCES livraria.pedidos(num_pedido),
CONSTRAINT fk_livropedido FOREIGN KEY(cod_livro) REFERENCES livraria.livros(cod_livro)
);

CREATE TABLE livraria.carrinholivro(
carrinho_sessao int,
cliente_cpf VARCHAR(11),
cod_livro int,
quantidade int NOT NULL,
CONSTRAINT pk_carrinholivro PRIMARY KEY (carrinho_sessao,cliente_cpf,cod_livro),
CONSTRAINT fk_carrinholivro FOREIGN KEY (carrinho_sessao,cliente_cpf) REFERENCES livraria.carrinho(sessao,cliente_cpf),
CONSTRAINT fk_livrocarrinho FOREIGN KEY (cod_livro) REFERENCES livraria.livros(cod_livro)
);

CREATE TABLE livraria.telefones(
num_telefone int NOT NULL,
cliente_cpf VARCHAR(11),
CONSTRAINT pk_telefone PRIMARY KEY (num_telefone,cliente_cpf),
CONSTRAINT fk_telefone FOREIGN KEY (cliente_cpf) REFERENCES livraria.cliente(cpf)
);


-- Carregando Dados 

-- Inserindo livros
INSERT INTO livraria.livros (titulo, autor, editora, preco, estoque) VALUES ('Dom Casmurro', 'Machado de Assis', 'Editora X', 39.90, 100);
INSERT INTO livraria.livros (titulo, autor, editora, preco, estoque) VALUES ('O Hobbit', 'J.R.R. Tolkien', 'Editora Y', 59.90, 50);
INSERT INTO livraria.livros (titulo, autor, editora, preco, estoque) VALUES ('1984', 'George Orwell', 'Editora Z', 29.90, 75);

-- Inserindo clientes
INSERT INTO livraria.cliente (cpf, pnome, snome, email, endereco) VALUES (12345678901, 'Maria', 'Silva', 'maria.silva@example.com', 'Rua A, 123');
INSERT INTO livraria.cliente (cpf, pnome, snome, email, endereco) VALUES (98765432100, 'João', 'Santos', 'joao.santos@example.com', 'Av. B, 456');
INSERT INTO livraria.cliente (cpf, pnome, snome, email, endereco) VALUES (55566677788, 'Ana', 'Oliveira', 'ana.oliveira@example.com', 'Rua C, 789');

-- Inserindo telefones dos clientes
INSERT INTO livraria.telefones (num_telefone, cliente_cpf) VALUES (1198765432, 12345678901);
INSERT INTO livraria.telefones (num_telefone, cliente_cpf) VALUES (1191234567, 98765432100);

-- Inserindo carrinhos
INSERT INTO livraria.carrinho (data_criacao, cliente_cpf) VALUES ('2024-08-09', 55566677788);

-- Buscando livro por titulo 
SELECT * FROM livraria.livros WHERE livraria.livros.titulo LIKE '%Casmurro%';

-- Buscando livro por autor 
SELECT * FROM livraria.livros WHERE livraria.livros.autor LIKE '%Assis%';

-- Adicionando livros ao carrinho
INSERT INTO livraria.carrinholivro (carrinho_sessao, cliente_cpf, cod_livro, quantidade) 
VALUES ((SELECT sessao FROM livraria.carrinho WHERE livraria.carrinho.cliente_cpf = '55566677788' AND livraria.carrinho.data_criacao = '2024-08-09'), 55566677788, (SELECT cod_livro FROM livraria.livros WHERE livraria.livros.titulo = 'Dom Casmurro'), 2);

INSERT INTO livraria.carrinholivro (carrinho_sessao, cliente_cpf, cod_livro, quantidade) 
VALUES ((SELECT sessao FROM livraria.carrinho WHERE livraria.carrinho.cliente_cpf = '55566677788' AND livraria.carrinho.data_criacao = '2024-08-09'), 55566677788, (SELECT cod_livro FROM livraria.livros WHERE livraria.livros.titulo = 'O Hobbit') , 1);

-- Inserindo pedidos
INSERT INTO livraria.pedidos (data_pedido, total_pedido, cliente_cpf) VALUES ('2024-08-10', 99.80, 12345678901);
INSERT INTO livraria.pedidos (data_pedido, total_pedido, cliente_cpf) VALUES ('2024-08-11', 59.90, 98765432100);

-- Buscando os pedidos 
SELECT * FROM livraria.pedidos 

-- Associando livros aos pedidos
INSERT INTO livraria.pedidolivro (num_pedido, cod_livro, quantidade) 
VALUES ((SELECT num_pedido FROM livraria.pedidos WHERE livraria.pedidos.cliente_cpf = '12345678901' AND livraria.pedidos.data_pedido = '2024-08-10'), 
        (SELECT cod_livro FROM livraria.livros WHERE livraria.livros.titulo = 'Dom Casmurro'), 
		1); 
		
INSERT INTO livraria.pedidolivro (num_pedido, cod_livro, quantidade) 
VALUES ((SELECT num_pedido FROM livraria.pedidos WHERE livraria.pedidos.cliente_cpf = '98765432100' AND livraria.pedidos.data_pedido = '2024-08-11'), 
        (SELECT cod_livro FROM livraria.livros WHERE livraria.livros.titulo = 'O Hobbit'), 
		1);
		
INSERT INTO livraria.pedidolivro (num_pedido, cod_livro, quantidade) 
VALUES ((SELECT num_pedido FROM livraria.pedidos WHERE livraria.pedidos.cliente_cpf = '98765432100' AND livraria.pedidos.data_pedido = '2024-08-11'), 
        (SELECT cod_livro FROM livraria.livros WHERE livraria.livros.titulo = '1984'), 
		1);
