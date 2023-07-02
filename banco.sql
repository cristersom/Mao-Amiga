CREATE TABLE cidade(
idCidade integer NOT NULL AUTO_INCREMENT,
cidade varchar(80) NOT NULL,
uf varchar(2) NOT NULL,
CONSTRAINT pk_Cidade PRIMARY KEY (idCidade),
CONSTRAINT uk_Cidade UNIQUE (cidade,uf)
);

CREATE TABLE cep(
idCep integer NOT NULL AUTO_INCREMENT,
cep varchar(9) NOT NULL,
logradouro varchar(80),
bairro varchar(80),
idCidade integer NOT NULL,
CONSTRAINT pk_Cep PRIMARY KEY (idCep),
CONSTRAINT fk_Cidade FOREIGN KEY (idCidade) REFERENCES cidade(idCidade)
);

CREATE TABLE curso(
idCurso integer NOT NULL AUTO_INCREMENT,
curso varchar(30) NOT NULL,
descricao varchar(80),
CONSTRAINT pk_Curso PRIMARY KEY (idCurso)
);

CREATE TABLE turma(
idTurma integer NOT NULL AUTO_INCREMENT,
idCurso integer NOT NULL,
turma varchar(10) NOT NULL,
descricao varchar(80),
ano integer NOT NULL,
data_inicio date NOT NULL,
data_fim date NOT NULL,
CONSTRAINT pk_Turma PRIMARY KEY (idTurma),
CONSTRAINT uk_Turma UNIQUE (turma, ano),
CONSTRAINT fk_Curso FOREIGN KEY (idCurso) REFERENCES curso(idCurso)
);

CREATE TABLE contrato_matricula(
idMatricula integer NOT NULL AUTO_INCREMENT,
idTurma integer NOT NULL,
idAluno integer NOT NULL,
CONSTRAINT pk_Contrato PRIMARY KEY (idMatricula),
CONSTRAINT uk_Contrato_Aluno UNIQUE (idTurma, idAluno),
CONSTRAINT fk_Turma FOREIGN KEY (idTurma) REFERENCES turma(idTurma)
);

CREATE TABLE aluno(
idAluno integer NOT NULL AUTO_INCREMENT,
cpf varchar(12) NOT NULL,
nome varchar(80) NOT NULL,
rg varchar(12),
certNascimento varchar(32),
sexo varchar(9),
nomeMae varchar(80),
nomePai varchar(80),
nacionalidade varchar(50),
idiomaMaterno varchar(50),
dataNascimento date NOT NULL,
idCep integer NOT NULL,
numero integer NOT NULL,
complemento varchar(80),
celular varchar(15),
foneComercial varchar(15),
eMail varchar(80),
tipo varchar(25) NOT NULL,
autorUsoImagem integer,
fichaSaude varchar(2048),
infoEducacao varchar(2048),
observacoes varchar(2048),
CONSTRAINT pk_Aluno PRIMARY KEY (idAluno),
CONSTRAINT uk_Aluno UNIQUE (cpf),
CONSTRAINT fk_Cep FOREIGN KEY (idCep) REFERENCES cep(idCep)
);

CREATE TABLE colaborador(
idColaborador integer NOT NULL AUTO_INCREMENT,
cpf varchar(12) NOT NULL,
nome varchar(80) NOT NULL,
rg varchar(12),
certNascimento varchar(32),
sexo varchar(9),
nomeMae varchar(80),
nomePai varchar(80),
nacionalidade varchar(50),
dataNascimento date NOT NULL,
idCep integer NOT NULL,
numero integer NOT NULL,
complemento varchar(80),
celular varchar(15),
foneComercial varchar(15),
eMail varchar(80),
tipo varchar(25) NOT NULL,
autorUsoImagem integer,
CONSTRAINT pk_Colaborador PRIMARY KEY (idColaborador),
CONSTRAINT uk_Colaborador UNIQUE (cpf, tipo),
CONSTRAINT fk_CepColaborador FOREIGN KEY (idCep) REFERENCES cep(idCep)
);

-- Tabela de junção entre turma e colaborador (muitos para muitos)
CREATE TABLE turma_colaborador (
  idColaboradorTurma integer NOT NULL AUTO_INCREMENT,
  idTurma integer NOT NULL,
  idColaborador integer NOT NULL,
  CONSTRAINT pk_Turma_Colaborador PRIMARY KEY (idColaboradorTurma),
  CONSTRAINT uk_Turma_Colaborador UNIQUE (idTurma, idColaborador),
  CONSTRAINT fk_Turma_TurmaColaborador FOREIGN KEY (idTurma) REFERENCES turma(idTurma),
  CONSTRAINT fk_Colaborador_TurmaColaborador FOREIGN KEY (idColaborador) REFERENCES colaborador(idColaborador)
);

CREATE TABLE responsavel(
idResponsavel integer NOT NULL AUTO_INCREMENT,
cpf varchar(12) NOT NULL,
nome varchar(80) NOT NULL,
rg varchar(12),
certNascimento varchar(32),
sexo varchar(9),
nomeMae varchar(80),
nomePai varchar(80),
nacionalidade varchar(50),
dataNascimento date NOT NULL,
idCep integer NOT NULL,
numero integer NOT NULL,
complemento varchar(80),
celular varchar(15),
foneComercial varchar(15),
eMail varchar(80),
localTrabalho varchar(50),
tipo varchar(25) NOT NULL,
autorUsoImagem integer,
CONSTRAINT pk_Responsavel PRIMARY KEY (idResponsavel),
CONSTRAINT uk_Responsavel UNIQUE (cpf, tipo),
CONSTRAINT fk_Responsavel FOREIGN KEY (idCep) REFERENCES cep(idCep)
);

CREATE TABLE aluno_responsaveis(
  idResponsavelAluno integer NOT NULL AUTO_INCREMENT,
  idMatricula integer NOT NULL,
  idResponsavel integer NOT NULL,
  CONSTRAINT pk_Responsaveis PRIMARY KEY (idResponsavelAluno),
  CONSTRAINT uk_Responsaveis UNIQUE (idMatricula, idResponsavel),
  CONSTRAINT fk_Resp_Matricula FOREIGN KEY (idMatricula) REFERENCES contrato_matricula(idMatricula) ON DELETE CASCADE,
  CONSTRAINT fk_Resp_Responsavel FOREIGN KEY (idResponsavel) REFERENCES responsavel(idResponsavel)
);

-- Tabela de junção entre aluno e autorizados (muitos para muitos)
CREATE TABLE aluno_autorizados(
  idAutorizado integer NOT NULL AUTO_INCREMENT,
  idAluno integer NOT NULL,
  nome varchar(80) NOT NULL,
  celular varchar(15),
  telefone varchar(15),
  tipo varchar(25) NOT NULL,
  dataInicio date NOT NULL,
  dataFim date NOT NULL,
  CONSTRAINT pk_Aluno_Autorizado PRIMARY KEY (idAutorizado, idAluno),
  CONSTRAINT fk_Aluno_AlunoAutorizado FOREIGN KEY (idAluno) REFERENCES aluno(idAluno)
);
