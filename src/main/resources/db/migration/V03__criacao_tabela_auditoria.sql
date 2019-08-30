CREATE TABLE auditoria
(
	codigo serial PRIMARY KEY,
	nome varchar(255),
	data date,
	loja_codigo BIGINT(20),
	finalizado boolean
);