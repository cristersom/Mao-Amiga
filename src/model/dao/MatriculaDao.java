package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
	
	public ArrayList<MatriculaBO> consultaPorMatricula(int idMatricula) {
		ArrayList<MatriculaBO> matriculaBOList = consulta("idMatricula = " + idMatricula, "idMatricula");
		return matriculaBOList;
	}
	
	public ArrayList<MatriculaBO> consultaPorTurma(int idTurma) {
		ArrayList<MatriculaBO> matriculaBOList = consulta("idTurma = " + idTurma, "idTurma");
		return matriculaBOList;
	}
	
	private ArrayList<MatriculaBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca
					.executeQuery("SELECT idMatricula, idTurma, mat.idAluno, nome, cpf, dataNascimento, nomeMae "
							+ "FROM contrato_matricula mat INNER JOIN aluno ON mat.idAluno = aluno.idAluno WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<MatriculaBO> matriculaBOList = new ArrayList<MatriculaBO>();
				do {
					MatriculaBO matriculaBO = new MatriculaBO();
					
					matriculaBO.setMatricula(Integer.parseInt(registros.getString("idMatricula")));
					matriculaBO.setTurma(Integer.parseInt(registros.getString("idTurma")));
					matriculaBO.setAluno(Integer.parseInt(registros.getString("idAluno")));
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
					"N�o foi poss�vel carregar os dados!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public boolean incluir(TurmaBO turmaBO) {
		
		try {
			String sql = "INSERT INTO contrato_matricula (idTurma, idAluno) VALUES (?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, turmaBO.getId());
			stmt.setInt(2, turmaBO.alunoBO.getId());

			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLIntegrityConstraintViolationException SQLError) {
			SQLError.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel incluir o aluno!\nAluno "+turmaBO.alunoBO.getNome()+" j� cadastrado na turma "+turmaBO.getId()+".\n" + "Mensagem: " + SQLError.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel realizar a inclus�o!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public boolean excluir(int idAluno) {
		try {
			String sql = "DELETE FROM contrato_matricula WHERE idAluno= " + idAluno;
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel realizar a opera��o!\n"
							+ "Mensagem: Esse registro est� sendo referenciado por outra tabela",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
}
