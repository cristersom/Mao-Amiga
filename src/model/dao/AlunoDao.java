package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import javax.swing.JOptionPane;

import model.bo.AlunoBO;
import model.exceptions.CepInvalidoException;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;

public class AlunoDao {
	Connection con;

	public AlunoDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<AlunoBO> consultaPorCodigo(int idAluno) {
		ArrayList<AlunoBO> alunoBOList = consulta("idAluno = " + idAluno, "idAluno");
		return alunoBOList;
	}

	public ArrayList<AlunoBO> consultaPorNome(String nome) {
		ArrayList<AlunoBO> alunoBOList = consulta("nome LIKE '%" + nome + "%'", "nome");
		return alunoBOList;
	}

	public ArrayList<AlunoBO> consultaPorCpf(String cpf) {
		ArrayList<AlunoBO> alunoBOList = consulta("cpf = '" + cpf + "'", "cpf");
		return alunoBOList;
	}

	private ArrayList<AlunoBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT idAluno, cpf, nome, rg, certNascimento, sexo, nomeMae, nomePai, nacionalidade, dataNascimento, celular, foneComercial"
					+ ", eMail, idiomaMaterno, tipo, autorUsoImagem, aluno.idCep, numero, complemento, cep, logradouro, bairro, cep.idCidade, cidade, uf "
					+ ", fichaSaude, infoEducacao, observacoes "
							+ "FROM aluno INNER JOIN cep ON aluno.idCep = cep.idCep "
							+ "INNER JOIN cidade ON cep.idCidade = cidade.idCidade" + " WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<AlunoBO> alunoBOList = new ArrayList<AlunoBO>();
				do {
					AlunoBO alunoBO = new AlunoBO();

					alunoBO.setId(Integer.parseInt(registros.getString("idAluno")));
					alunoBO.setAutorUsoImagem(Integer.parseInt(registros.getString("autorUsoImagem")));
					
					try {
						alunoBO.setCpf(registros.getString("cpf"));
						alunoBO.setNome(registros.getString("nome"));
						alunoBO.setNomeMae(registros.getString("nomeMae"));
					} catch (StringVaziaException | CpfInvalidoException e1) {
					}
					alunoBO.setRg(registros.getString("rg"));
					alunoBO.setCertNascimento(registros.getString("certNascimento"));
					alunoBO.setSexo(registros.getString("sexo"));
					alunoBO.setNomePai(registros.getString("nomePai"));
					alunoBO.setNacionalidade(registros.getString("nacionalidade"));
					alunoBO.setCelular(registros.getString("celular"));
					alunoBO.setFoneComercial(registros.getString("foneComercial"));
					alunoBO.setEmail(registros.getString("eMail"));
					alunoBO.setIdiomaMaterno(registros.getString("idiomaMaterno"));
					alunoBO.setTipo(registros.getString("tipo"));
					alunoBO.setFichaSaude(registros.getString("fichaSaude"));
					alunoBO.setInfoEducacao(registros.getString("infoEducacao"));
					alunoBO.setObservacoes(registros.getString("observacoes"));
					
					alunoBO.cep.setId(Integer.parseInt(registros.getString("idCep")));
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataNascimento"));
					alunoBO.setDataNascimento(data);
					alunoBO.setComplemento(registros.getString("complemento"));
					try {
						alunoBO.cep.setCep(registros.getString("cep"));
						alunoBO.cep.cidade.setCidade(registros.getString("cidade"));
						alunoBO.setNumero(Integer.parseInt(registros.getString("numero")));
					} catch (StringVaziaException | CepInvalidoException e) {
					}
					alunoBO.cep.setLogradouro(registros.getString("logradouro"));
					alunoBO.cep.setBairro(registros.getString("bairro"));
					alunoBO.cep.cidade.setId(Integer.parseInt(registros.getString("idCidade")));
					alunoBO.cep.cidade.setUf(registros.getString("uf"));

					alunoBOList.add(alunoBO);
				} while (registros.next());
				return alunoBOList;
			}
			sentenca.close();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível carregar os dados!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public boolean incluir(AlunoBO alunoBO) {
		try {
			String sql = "INSERT INTO aluno (cpf, nome, rg, certNascimento, sexo, nomeMae, nomePai, nacionalidade"
					+ ", dataNascimento, idCep, numero, complemento, celular, foneComercial, eMail, idiomaMaterno, tipo, autorUsoImagem"
					+ ", fichaSaude, infoEducacao, observacoes)"
					+ "VALUES (?, UPPER(?), ?, ?, ?, UPPER(?), UPPER(?), UPPER(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, alunoBO.getCpf());
			stmt.setString(2, alunoBO.getNome());
			stmt.setString(3, alunoBO.getRg());
			stmt.setString(4, alunoBO.getCertNascimento());
			stmt.setString(5, alunoBO.getSexo());
			stmt.setString(6, alunoBO.getNomeMae());
			stmt.setString(7, alunoBO.getNomePai());
			stmt.setString(8, alunoBO.getNacionalidade());
			stmt.setDate(9, new Date(alunoBO.getDataNascimento().getTimeInMillis()));
			stmt.setInt(10, alunoBO.cep.getId());
			stmt.setInt(11, alunoBO.getNumero());
			stmt.setString(12, alunoBO.getComplemento());
			stmt.setString(13, alunoBO.getCelular());
			stmt.setString(14, alunoBO.getFoneComercial());
			stmt.setString(15, alunoBO.getEmail());
			stmt.setString(16, alunoBO.getIdiomaMaterno());
			stmt.setString(17, alunoBO.getTipo());
			stmt.setInt(18, alunoBO.getAutorUsoImagem());
			stmt.setString(19, alunoBO.getFichaSaude());
			stmt.setString(20, alunoBO.getInfoEducacao());
			stmt.setString(21, alunoBO.getObservacoes());
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a inclusão!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean alterar(AlunoBO alunoBO) {
		try {
			
			String sql = "UPDATE aluno SET cpf = ?, nome = UPPER(?), rg = ?, certNascimento = ?, sexo = ?, nomeMae = UPPER(?), nomePai = UPPER(?), nacionalidade = UPPER(?)"
					+ ", dataNascimento = ?, idCep = ?, numero = ?, complemento = ?, celular = ?, foneComercial = ?, eMail = ?, idiomaMaterno = ?, tipo = ?, autorUsoImagem = ?"
					+ ", fichaSaude = ?, infoEducacao = ?, observacoes = ?"
					+ " WHERE idAluno = " + alunoBO.getId();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, alunoBO.getCpf());
			stmt.setString(2, alunoBO.getNome());
			stmt.setString(3, alunoBO.getRg());
			stmt.setString(4, alunoBO.getCertNascimento());
			stmt.setString(5, alunoBO.getSexo());
			stmt.setString(6, alunoBO.getNomeMae());
			stmt.setString(7, alunoBO.getNomePai());
			stmt.setString(8, alunoBO.getNacionalidade());
			stmt.setDate(9, new Date(alunoBO.getDataNascimento().getTimeInMillis()));
			stmt.setInt(10, alunoBO.cep.getId());
			stmt.setInt(11, alunoBO.getNumero());
			stmt.setString(12, alunoBO.getComplemento());
			stmt.setString(13, alunoBO.getCelular());
			stmt.setString(14, alunoBO.getFoneComercial());
			stmt.setString(15, alunoBO.getEmail());
			stmt.setString(16, alunoBO.getIdiomaMaterno());
			stmt.setString(17, alunoBO.getTipo());
			stmt.setInt(18, alunoBO.getAutorUsoImagem());
			stmt.setString(19, alunoBO.getFichaSaude());
			stmt.setString(20, alunoBO.getInfoEducacao());
			stmt.setString(21, alunoBO.getObservacoes());
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a operação!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean excluir(int idAluno) {
		try {
			String sql = "DELETE FROM aluno WHERE idAluno= " + idAluno;
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a operação!\n"
							+ "Mensagem: Esse registro está sendo referenciado por outra tabela",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	
	public boolean incluirAluno(int idTurma, int idAluno) {
		try {
			String sql = "INSERT INTO turma_aluno (idTurma, idAluno) VALUES (?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idTurma);
			stmt.setInt(2, idAluno);
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLIntegrityConstraintViolationException iSQL) {
			iSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a inclusão!\nAluno selecionado já está incluso na turma!\n" + "Mensagem: " + iSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a inclusão!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public boolean excluirAlunoTurma(int idAluno) {
		try {
			String sql = "DELETE FROM turma_aluno WHERE idAluno= " + idAluno;
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a operação!\n"
							+ "Mensagem: Esse registro está sendo referenciado por outra tabela",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public ArrayList<AlunoBO> consultaPorTurma(int idTurma) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca
					.executeQuery("SELECT col.idAluno, nome,  tipo, cpf, dataNascimento, nomeMae "
							+ "FROM turma_aluno INNER JOIN aluno col on turma_aluno.idAluno = col.idAluno "
							+ "WHERE idTurma = " + idTurma
							+ " Order By tipo, nome");

			if (registros.next()) {
				ArrayList<AlunoBO> alunoBOList = new ArrayList<AlunoBO>();
				do {
					AlunoBO alunoBO = new AlunoBO();
			
					alunoBO.setId(Integer.parseInt(registros.getString("idAluno")));
					alunoBO.setTipo(registros.getString("tipo"));	
					try {
						alunoBO.setNome(registros.getString("nome"));
						alunoBO.setCpf(registros.getString("cpf"));
						alunoBO.setNomeMae(registros.getString("nomeMae"));
					} catch (StringVaziaException | CpfInvalidoException e) {
					}
						
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataNascimento"));
					alunoBO.setDataNascimento(data);
				
					
					alunoBOList.add(alunoBO);
				} while (registros.next());
				return alunoBOList;
			}
			sentenca.close();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível carregar os dados!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
}
