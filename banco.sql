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
CONSTRAINT uk_Turma UNIQUE (turma, ano),
CONSTRAINT fk_Curso FOREIGN KEY (codCurso) REFERENCES curso(codCurso)
);

CREATE TABLE contrato_matricula(
codMatricula integer NOT NULL AUTO_INCREMENT,
codTurma integer NOT NULL,
codAluno integer NOT NULL,
CONSTRAINT pk_Contrato PRIMARY KEY (codMatricula),
CONSTRAINT uk_Contrato_Aluno UNIQUE (codTurma, codAluno),
CONSTRAINT fk_Turma FOREIGN KEY (codTurma) REFERENCES turma(codTurma)
);

CREATE TABLE aluno(
codAluno integer NOT NULL AUTO_INCREMENT,
cpf varchar(12) NOT NULL,
nome varchar(80) NOT NULL,
nomeMae varchar(80) NOT NULL,
dataNascimento date NOT NULL,
codCep integer NOT NULL,
numero integer NOT NULL,
complemento varchar(80),
CONSTRAINT pk_Aluno PRIMARY KEY (codAluno),
CONSTRAINT uk_Aluno UNIQUE (cpf),
CONSTRAINT fk_Cep FOREIGN KEY (codCep) REFERENCES cep(codCep)
);

CREATE TABLE colaborador(
codColaborador integer NOT NULL AUTO_INCREMENT,
cpf varchar(12) NOT NULL,
nome varchar(80) NOT NULL,
rg varchar(12),
certNascimento varchar(32),
sexo varchar(9),
nomeMae varchar(80),
nomePai varchar(80),
nacionalidade varchar(50),
dataNascimento date NOT NULL,
codCep integer NOT NULL,
numero integer NOT NULL,
complemento varchar(80),
celular varchar(15),
foneComercial varchar(15),
eMail varchar(80),
tipo varchar(10) NOT NULL,
autorUsoImagem integer,
CONSTRAINT pk_Colaborador PRIMARY KEY (codColaborador),
CONSTRAINT uk_Colaborador UNIQUE (cpf, tipo),
CONSTRAINT fk_CepColaborador FOREIGN KEY (codCep) REFERENCES cep(codCep)
);

-- Tabela de junção entre turma e colaborador (muitos para muitos)
CREATE TABLE turma_colaborador (
  codTurma integer NOT NULL,
  codColaborador integer NOT NULL,
  CONSTRAINT pk_Turma_Colaborador PRIMARY KEY (codTurma, codColaborador),
  CONSTRAINT fk_Turma_TurmaColaborador FOREIGN KEY (codTurma) REFERENCES turma(codTurma),
  CONSTRAINT fk_Colaborador_TurmaColaborador FOREIGN KEY (codColaborador) REFERENCES colaborador(codColaborador)
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