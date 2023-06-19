CREATE TABLE cidade(
codCidade integer NOT NULL AUTO_INCREMENT,
cidade varchar(80) NOT NULL,
uf varchar(2) NOT NULL,
CONSTRAINT pk_Cidade PRIMARY KEY (codCidade),
CONSTRAINT uk_Cidade UNIQUE (cidade,uf)
);

CREATE TABLE cep(
codCep integer NOT NULL AUTO_INCREMENT,
cep varchar(9) NOT NULL,
logradouro varchar(80),
bairro varchar(80),
codCidade integer NOT NULL,
CONSTRAINT pk_Cep PRIMARY KEY (codCep),
CONSTRAINT fk_Cidade FOREIGN KEY (codCidade) REFERENCES cidade(codCidade)
);