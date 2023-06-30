package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
					"SELECT idAluno, cpf, nome, dataNascimento, aluno.idCep, numero, complemento, cep, logradouro, bairro, cep.idCidade, cidade, uf, "
							+ "nomeMae "
							+ "FROM aluno INNER JOIN cep ON aluno.idCep = cep.idCep "
							+ "INNER JOIN cidade ON cep.idCidade = cidade.idCidade" + " WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<AlunoBO> alunoBOList = new ArrayList<AlunoBO>();
				do {
					AlunoBO alunoBO = new AlunoBO();

					alunoBO.setId(Integer.parseInt(registros.getString("idAluno")));
					
					try {
						alunoBO.setCpf(registros.getString("cpf"));
						alunoBO.setNome(registros.getString("nome"));	
					} catch (StringVaziaException | CpfInvalidoException e1) {
					}
					
					alunoBO.cep.setId(Integer.parseInt(registros.getString("idCep")));
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataNascimento"));
					alunoBO.setDataNascimento(data);
					alunoBO.setComplemento(registros.getString("complemento"));
					try {
						alunoBO.cep.setCep(registros.getString("cep"));
						alunoBO.cep.cidade.setCidade(registros.getString("cidade"));
						alunoBO.setNumero(Integer.parseInt(registros.getString("numero")));
						alunoBO.setNomeMae(registros.getString("nomeMae"));
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
			String sql = "INSERT INTO aluno (cpf, nome, dataNascimento, idCep, numero, complemento) VALUES (?,UPPER(?),?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, alunoBO.getCpf());
			stmt.setString(2, alunoBO.getNome());
			stmt.setDate(3, new Date(alunoBO.getDataNascimento().getTimeInMillis()));
			stmt.setInt(4, alunoBO.cep.getId());
			stmt.setInt(5, alunoBO.getNumero());
			stmt.setString(6, alunoBO.getComplemento());
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
			
			String sql = "UPDATE aluno SET cpf = ?, nome = ?, dataNascimento = ?, idCep = ?, numero = ?, complemento = ?"
					+ "WHERE idAluno = " + alunoBO.getId();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, alunoBO.getCpf());
			stmt.setString(2, alunoBO.getNome());
			stmt.setDate(3, new Date(alunoBO.getDataNascimento().getTimeInMillis()));
			stmt.setInt(4, alunoBO.cep.getId());
			stmt.setInt(5, alunoBO.getNumero());
			stmt.setString(6, alunoBO.getComplemento());
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

}
