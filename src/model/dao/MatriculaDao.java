package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.MatriculaBO;
import model.bo.TurmaBO;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;


public class MatriculaDao {
	Connection con;
	
	public MatriculaDao() {
		con = Conexao.conectaBanco();
	}
	
	public ArrayList<MatriculaBO> consultaPorMatricula(int codMatricula) {
		ArrayList<MatriculaBO> matriculaBOList = consulta("codMatricula = " + codMatricula, "codMatricula");
		return matriculaBOList;
	}
	
	public ArrayList<MatriculaBO> consultaPorTurma(int codTurma) {
		ArrayList<MatriculaBO> matriculaBOList = consulta("codTurma = " + codTurma, "codTurma");
		return matriculaBOList;
	}
	
	private ArrayList<MatriculaBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca
					.executeQuery("SELECT codMatricula, codTurma, mat.codAluno, nome, cpf, dataNascimento, nomeMae "
							+ "FROM contrato_matricula mat INNER JOIN aluno ON mat.codAluno = aluno.codAluno WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<MatriculaBO> matriculaBOList = new ArrayList<MatriculaBO>();
				do {
					MatriculaBO matriculaBO = new MatriculaBO();
					
					matriculaBO.setMatricula(Integer.parseInt(registros.getString("codMatricula")));
					matriculaBO.setTurma(Integer.parseInt(registros.getString("codTurma")));
					matriculaBO.setAluno(Integer.parseInt(registros.getString("codAluno")));
					try {
						matriculaBO.turmaBO.alunoBO.setNome(registros.getString("nome"));
						matriculaBO.turmaBO.alunoBO.setCpf(registros.getString("cpf"));
						matriculaBO.turmaBO.alunoBO.setNomeMae(registros.getString("nomeMae"));
						matriculaBO.turmaBO.alunoBO.setDataNascimento(registros.getString("dataNascimento"));
					} catch (StringVaziaException | CpfInvalidoException | ParseException e1) {
					}
					
					/*
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataNascimento"));
					matriculaBO.turmaBO.alunoBO.setDataNascimento(data);
					*/
					matriculaBOList.add(matriculaBO);
				} while (registros.next());
				return matriculaBOList;
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

	public boolean incluir(TurmaBO turmaBO) {
		
		try {
			String sql = "INSERT INTO contrato_matricula (codTurma, codAluno) VALUES (?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, turmaBO.getCodigo());
			stmt.setInt(2, turmaBO.alunoBO.getCodigo());

			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLIntegrityConstraintViolationException SQLError) {
			SQLError.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível incluir o aluno!\nAluno "+turmaBO.alunoBO.getNome()+" já cadastrado na turma "+turmaBO.getCodigo()+".\n" + "Mensagem: " + SQLError.getMessage(), "Erro",
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
	
	public boolean excluir(int codAluno) {
		try {
			String sql = "DELETE FROM contrato_matricula WHERE codAluno= " + codAluno;
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
