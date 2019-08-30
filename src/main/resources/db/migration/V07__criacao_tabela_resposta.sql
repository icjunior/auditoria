CREATE TABLE resposta_auditoria
(
	codigo serial,
	pergunta_codigo bigint(20),
	auditoria_codigo bigint(20),
	comentario varchar(255),
	satisfatorio boolean,
	CONSTRAINT pk_resposta PRIMARY KEY(codigo) 
);