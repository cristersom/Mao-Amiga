CREATE TABLE curso(
codCurso integer NOT NULL AUTO_INCREMENT,
curso varchar(30) NOT NULL,
descricao varchar(80),
CONSTRAINT pk_Curso PRIMARY KEY (codCurso)
);

CREATE TABLE turma(
codTurma integer NOT NULL AUTO_INCREMENT,
codCurso integer NOT NULL,
turma varchar(10) NOT NULL,
descricao varchar(80),
ano integer NOT NULL,
data_inicio date NOT NULL,
data_fim date NOT NULL,
CONSTRAINT pk_Turma PRIMARY KEY (codTurma),
CONSTRAINT fk_Curso FOREIGN KEY (codCurso) REFERENCES curso(codCurso)
);

CREATE TABLE contrato_matricula(
codContrato integer NOT NULL AUTO_INCREMENT,
codTurma integer NOT NULL,
CONSTRAINT pk_Contrato PRIMARY KEY (codContrato),
CONSTRAINT fk_Turma FOREIGN KEY (codTurma) REFERENCES turma(codTurma)
);

CREATE TABLE aluno(
codAluno integer NOT NULL AUTO_INCREMENT,
cpf varchar(12) NOT NULL,
nome varchar(80) NOT NULL,
dataNascimento date NOT NULL,
codCep integer NOT NULL,
numero integer NOT NULL,
complemento varchar(80),
CONSTRAINT pk_Aluno PRIMARY KEY (codAluno),
CONSTRAINT uk_Aluno UNIQUE (cpf),
CONSTRAINT fk_Cep FOREIGN KEY (codCep) REFERENCES cep(codCep)
);

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