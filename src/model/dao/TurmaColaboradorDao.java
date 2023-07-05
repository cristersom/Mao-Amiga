package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import model.bo.ColaboradorBO;
import model.bo.CursoBO;
import model.bo.TurmaColaboradorBO;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;
import view.Utils;

public class TurmaColaboradorDao {
	Connection con;
	public TurmaColaboradorDao() {
		con = Conexao.conectaBanco();
	}
	
	public ArrayList<TurmaColaboradorBO> consultaPorTurma(int idTurma) {
		ArrayList<TurmaColaboradorBO> turmaColaboradorBOList = consulta("idTurma = " + idTurma, "idTurma");
		return turmaColaboradorBOList;
	}

	public ArrayList<TurmaColaboradorBO> consultaProfessores(int idTurma) {
		ArrayList<TurmaColaboradorBO> turmaColaboradorBOList = consulta("idTurma = " + idTurma
				+ " && tipo = '" + Utils.Tipo.Professor.toString() + "'", "nome");
		return turmaColaboradorBOList;
	}
	
	public TurmaColaboradorBO consultaProfessor(int idTurma, int idColaborador) {
		ArrayList<TurmaColaboradorBO> turmaColaboradorBOList = consulta("idTurma = " + idTurma
				+ " && col.idColaborador = " + idColaborador
				+ " && tipo = '" + Utils.Tipo.Professor.toString() + "'", "nome");
		return turmaColaboradorBOList.get(0);
	}
	
	public ArrayList<TurmaColaboradorBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca
					.executeQuery("SELECT idColaboradorTurma, idTurma, col.idColaborador, nome,  tipo, cpf, dataNascimento, nomeMae "
							+ "FROM turma_colaborador INNER JOIN colaborador col ON turma_colaborador.idColaborador = col.idColaborador "
							+ "WHERE " + sentencaSQL + " Order By " + ordem );

			if (registros.next()) {
				ArrayList<TurmaColaboradorBO> TurmaColaboradorBOList = new ArrayList<TurmaColaboradorBO>();
				do {
					TurmaColaboradorBO turmaColaboradorBO = new TurmaColaboradorBO();
			
					turmaColaboradorBO.setIdColaboradorTurma(Integer.parseInt(registros.getString("idColaboradorTurma")));
					turmaColaboradorBO.setIdTurma(Integer.parseInt(registros.getString("idTurma")));
					turmaColaboradorBO.colaboradorBO.setId(Integer.parseInt(registros.getString("idColaborador")));
					turmaColaboradorBO.colaboradorBO.setTipo(registros.getString("tipo"));	
					try {
						turmaColaboradorBO.colaboradorBO.setNome(registros.getString("nome"));
						turmaColaboradorBO.colaboradorBO.setCpf(registros.getString("cpf"));
						turmaColaboradorBO.colaboradorBO.setNomeMae(registros.getString("nomeMae"));
					} catch (StringVaziaException | CpfInvalidoException e) {
					}
						
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataNascimento"));
					turmaColaboradorBO.colaboradorBO.setDataNascimento(data);
				
					
					TurmaColaboradorBOList.add(turmaColaboradorBO);
				} while (registros.next());
				return TurmaColaboradorBOList;
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
	
	public boolean incluir(int idTurma, int idColaborador) {
		try {
			String sql = "INSERT INTO turma_colaborador (idTurma, idColaborador) VALUES (?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idTurma);
			stmt.setInt(2, idColaborador);
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLIntegrityConstraintViolationException iSQL) {
			iSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a inclusão!\nColaborador selecionado já está incluso na turma!\n" + "Mensagem: " + iSQL.getMessage(), "Erro",
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
	
	public boolean excluir(int idColaboradorTurma) {
		try {
			String sql = "DELETE FROM turma_colaborador WHERE idColaboradorTurma= " + idColaboradorTurma;
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
