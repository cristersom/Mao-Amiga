CREATE TABLE cidade(
codCidade integer NOT NULL AUTO_INCREMENT,
cidade varchar(80) NOT NULL,
uf varchar(2) NOT NULL,
CONSTRAINT pk_Cidade PRIMARY KEY (codCidade),
CONSTRAINT uk_Cidade UNIQUE (cidade,uf)
);

CREATE TABLE bairro(
codBairro integer NOT NULL AUTO_INCREMENT,
bairro varchar(80) NOT NULL,
codCidade integer NOT NULL,
CONSTRAINT pk_Bairro PRIMARY KEY (codBairro),
CONSTRAINT uk_Cidade UNIQUE (bairro,codCidade),
CONSTRAINT fk_Cidade FOREIGN KEY (codCidade) REFERENCES cidade(codCidade)
);

CREATE TABLE cep(
codCep integer NOT NULL AUTO_INCREMENT,
cep varchar(9) NOT NULL,
endereco varchar(80),
codBairro integer NOT NULL,
codCidade integer NOT NULL,
CONSTRAINT pk_Cep PRIMARY KEY (codCep),
CONSTRAINT uk_Cep UNIQUE (cep,endereco,codBairro,codCidade),
CONSTRAINT fk_Bairro FOREIGN KEY (codBairro) REFERENCES bairro(codBairro),
CONSTRAINT fk_Cidade_cep FOREIGN KEY (codCidade) REFERENCES cidade(codCidade)
);






SELECT codBairro, bairro, cidade, uf, cidade.codcidade
FROM bairro INNER JOIN cidade ON bairro.codCidade = cidade.codCidade WHERE
