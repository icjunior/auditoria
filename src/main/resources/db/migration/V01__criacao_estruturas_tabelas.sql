CREATE TABLE loja
(
	codigo serial,
	nome varchar(100),
	apelido varchar(50),
	endereco varchar(255),
	numero varchar(10),
	bairro varchar(100),
	cidade varchar(100),
	estado varchar(2),
	telefone varchar(14),
	cnpj varchar(15),
	ie varchar(15),
	CONSTRAINT pk_loja PRIMARY KEY(codigo) 
) ;

CREATE TABLE tipo
(
	codigo serial,
	nome varchar(255),
	CONSTRAINT pk_tipo PRIMARY KEY(codigo)
) ;

CREATE TABLE setor
(
	codigo serial,
	nome varchar(100),
	tipo_codigo BIGINT(20),
	CONSTRAINT pk_setor PRIMARY KEY(codigo),
	CONSTRAINT fk_setor_tipo FOREIGN KEY (tipo_codigo) REFERENCES tipo(codigo)
) ;

CREATE TABLE pergunta
(
	codigo serial,
	nome text,
	setor_codigo BIGINT(20),
	CONSTRAINT pk_pergunta PRIMARY KEY(codigo),
	CONSTRAINT fk_pergunta_setor FOREIGN KEY (setor_codigo) REFERENCES setor(codigo)
) ;

CREATE TABLE usuario
(
	codigo serial,
	nome varchar(100),
	login varchar(50),
	senha varchar(120),
	ativo boolean,
	CONSTRAINT pk_usuario PRIMARY KEY(codigo)
) ;