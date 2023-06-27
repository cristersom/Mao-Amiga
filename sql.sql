-- Tabelas existentes

CREATE TABLE curso (
  codCurso integer NOT NULL AUTO_INCREMENT,
  curso varchar(30) NOT NULL,
  descricao varchar(80),
  CONSTRAINT pk_Curso PRIMARY KEY (codCurso)
);

CREATE TABLE turma (
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

CREATE TABLE contrato_matricula (
  codMatricula integer NOT NULL AUTO_INCREMENT,
  codTurma integer NOT NULL,
  codAluno integer NOT NULL,
  CONSTRAINT pk_Contrato PRIMARY KEY (codMatricula),
  CONSTRAINT uk_Contrato_Aluno UNIQUE (codTurma, codAluno),
  CONSTRAINT fk_Turma FOREIGN KEY (codTurma) REFERENCES turma(codTurma)
);

CREATE TABLE aluno (
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

CREATE TABLE colaborador (
  codColaborador integer NOT NULL AUTO_INCREMENT,
  cpf varchar(12) NOT NULL,
  nome varchar(80) NOT NULL,
  rg varchar(12),
  certNascimento varchar(32),
  sexo varchar(8),
  nomeMae varchar(80),
  nomePai varchar(80),
  nacionalidade varchar(50),
  dataNascimento date NOT NULL,
  codCep integer NOT NULL,
  numero integer NOT NULL,
  complemento varchar(80),
  celular varchar(14),
  foneComercial varchar(14),
  eMail varchar(80),
  tipo varchar(10) NOT NULL,
  autorUsoImagem integer,
  CONSTRAINT pk_Colaborador PRIMARY KEY (codColaborador),
  CONSTRAINT uk_Colaborador UNIQUE (cpf, tipo),
  CONSTRAINT fk_CepColaborador FOREIGN KEY (codCep) REFERENCES cep(codCep)
);

CREATE TABLE cidade (
  codCidade integer NOT NULL AUTO_INCREMENT,
  cidade varchar(80) NOT NULL,
  uf varchar(2) NOT NULL,
  CONSTRAINT pk_Cidade PRIMARY KEY (codCidade),
  CONSTRAINT uk_Cidade UNIQUE (cidade, uf)
);

CREATE TABLE cep (
  codCep integer NOT NULL AUTO_INCREMENT,
  cep varchar(9) NOT NULL,
  logradouro varchar(80),
  bairro varchar(80),
  codCidade integer NOT NULL,
  CONSTRAINT pk_Cep PRIMARY KEY (codCep),
  CONSTRAINT fk_Cidade FOREIGN KEY (codCidade) REFERENCES cidade(codCidade)
);

-- Relacionamentos adicionados

-- Relacionamento entre curso e turma (um para muitos)
ALTER TABLE turma ADD CONSTRAINT fk_Curso_Turma FOREIGN KEY (codCurso) REFERENCES curso(codCurso);

-- Tabela de junção entre turma e colaborador (muitos para muitos)
CREATE TABLE turma_colaborador (
  codTurma integer NOT NULL,
  codColaborador integer NOT NULL,
  CONSTRAINT pk_Turma_Colaborador PRIMARY KEY (codTurma, codColaborador),
  CONSTRAINT fk_Turma_TurmaColaborador FOREIGN KEY (codTurma) REFERENCES turma(codTurma),
  CONSTRAINT fk_Colaborador_TurmaColaborador FOREIGN KEY (codColaborador) REFERENCES colaborador(codColaborador)
);

-- Adição da coluna "tipo" na tabela pessoa para representar a herança
ALTER TABLE pessoa ADD tipo varchar(10);

-- Tabela para armazenar os atributos específicos dos responsáveis
CREATE TABLE responsavel (
  codResponsavel integer NOT NULL AUTO_INCREMENT,
  codPessoa integer NOT NULL,
  CONSTRAINT pk_Responsavel PRIMARY KEY (codResponsavel),
  CONSTRAINT fk_Pessoa_Responsavel FOREIGN KEY (codPessoa) REFERENCES pessoa(codPessoa)
);

-- Tabela de junção entre aluno e autorizados (muitos para muitos)
CREATE TABLE aluno_autorizado (
  codAluno integer NOT NULL,
  codAutorizado integer NOT NULL,
  CONSTRAINT pk_Aluno_Autorizado PRIMARY KEY (codAluno, codAutorizado),
  CONSTRAINT fk_Aluno_AlunoAutorizado FOREIGN KEY (codAluno) REFERENCES aluno(codAluno),
  CONSTRAINT fk_Autorizado_AlunoAutorizado FOREIGN KEY (codAutorizado) REFERENCES autorizados(codAutorizado)
);

-- Adição da coluna "tipo" na tabela pessoa para representar a herança
ALTER TABLE pessoa ADD tipo varchar(10);

-- Adição da coluna "codEndereco" na tabela pessoa para representar a relação de 1 para muitos com endereço
ALTER TABLE pessoa ADD codEndereco integer;

-- Relacionamento 1 para muitos entre pessoa e endereço
ALTER TABLE pessoa ADD CONSTRAINT fk_Endereco_Pessoa FOREIGN KEY (codEndereco) REFERENCES endereco(codEndereco);

-- Adição da coluna "codCep" na tabela endereco para representar a relação de 1 para 1 com cep
ALTER TABLE endereco ADD codCep integer;

-- Relacionamento 1 para 1 entre endereco e cep
ALTER TABLE endereco ADD CONSTRAINT fk_Cep_Endereco FOREIGN KEY (codCep) REFERENCES cep(codCep);

-- Adição da coluna "codBairro" na tabela cep para representar a relação de 1 para 1 com bairro
ALTER TABLE cep ADD codBairro integer;

-- Relacionamento 1 para 1 entre cep e bairro
ALTER TABLE cep ADD CONSTRAINT fk_Bairro_Cep FOREIGN KEY (codBairro) REFERENCES bairro(codBairro);

-- Adição da coluna "codCidade" na tabela bairro para representar a relação de 1 para 1 com cidade
ALTER TABLE bairro ADD codCidade integer;

-- Relacionamento 1 para 1 entre bairro e cidade
ALTER TABLE bairro ADD CONSTRAINT fk_Cidade_Bairro FOREIGN KEY (codCidade) REFERENCES cidade(codCidade);

-- Relacionamento entre contrato_matricula e parecer (um para muitos)
ALTER TABLE parecer ADD codContratoMatricula integer;
ALTER TABLE parecer ADD CONSTRAINT fk_ContratoMatricula_Parecer FOREIGN KEY (codContratoMatricula) REFERENCES contrato_matricula(codMatricula);

-- Relacionamento entre contrato_matricula e frequencia (um para muitos)
ALTER TABLE frequencia ADD codContratoMatricula integer;
ALTER TABLE frequencia ADD CONSTRAINT fk_ContratoMatricula_Frequencia FOREIGN KEY (codContratoMatricula) REFERENCES contrato_matricula(codMatricula);

-- Relacionamento entre contrato_matricula e responsavel (um para muitos)
ALTER TABLE responsavel ADD codContratoMatricula integer;
ALTER TABLE responsavel ADD CONSTRAINT fk_ContratoMatricula_Responsavel FOREIGN KEY (codContratoMatricula) REFERENCES contrato_matricula(codMatricula);

-- Relacionamento entre contrato_matricula e aluno (um para muitos)
ALTER TABLE contrato_matricula ADD codAluno integer;
ALTER TABLE contrato_matricula ADD CONSTRAINT fk_Aluno_ContratoMatricula FOREIGN KEY (codAluno) REFERENCES aluno(codAluno);

-- Adição da coluna "codPessoa" na tabela aluno para representar a relação de 1 para 1 com pessoa
ALTER TABLE aluno ADD codPessoa integer;

-- Relacionamento 1 para 1 entre aluno e pessoa
ALTER TABLE aluno ADD CONSTRAINT fk_Pessoa_Aluno FOREIGN KEY (codPessoa) REFERENCES pessoa(codPessoa);

-- Adição da coluna "codPessoa" na tabela responsavel para representar a relação de 1 para muitos com pessoa
ALTER TABLE responsavel ADD codPessoa integer;

-- Relacionamento 1 para muitos entre responsavel e pessoa
ALTER TABLE responsavel ADD CONSTRAINT fk_Pessoa_Responsavel FOREIGN KEY (codPessoa) REFERENCES pessoa(codPessoa);
