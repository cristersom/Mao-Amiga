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
import model.bo.TurmaBO;
import model.exceptions.StringVaziaException;


public class TurmaDao {
	Connection con;
	public TurmaDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<TurmaBO> consultaPorCodigo(int idTurma) {
		ArrayList<TurmaBO> turmaBOList = consulta("idTurma = " + idTurma, "idTurma");
		return turmaBOList;
	}
	
	public ArrayList<TurmaBO> consultaPorTurma(String turma) {
		ArrayList<TurmaBO> turmaBOList = consulta("turma = UPPER('" + turma + "') OR turma LIKE UPPER('%" + turma + "%')", "idTurma");
		return turmaBOList;
	}
	
	public ArrayList<TurmaBO> consultaPorAno(int ano) {
		ArrayList<TurmaBO> turmaBOList = consulta("ano = " + ano, "ano");
		return turmaBOList;
	}
	
	public ArrayList<TurmaBO> consultaPorDescricao(String descricao) {
		ArrayList<TurmaBO> turmaBOList = consulta("descricao LIKE '%" + descricao + "%'", "descricao");
		return turmaBOList;
	}
	
	
	private ArrayList<TurmaBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca
					.executeQuery("SELECT * FROM turma WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<TurmaBO> turmaBOList = new ArrayList<TurmaBO>();
				do {
					TurmaBO turmaBO = new TurmaBO();
			
					turmaBO.setId(Integer.parseInt(registros.getString("idTurma")));
					turmaBO.cursoBO.setId(Integer.parseInt(registros.getString("idCurso")));
					try {
						turmaBO.setTurma(registros.getString("turma"));

					} catch (StringVaziaException e) {}
					turmaBO.setDescricao(registros.getString("descricao"));
					turmaBO.setAno(Integer.parseInt(registros.getString("ano")));
					
					
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("data_inicio"));
					turmaBO.setDataInicio(data);
					
					Calendar data2 = Calendar.getInstance();
					data2.setTime(registros.getDate("data_fim"));
					turmaBO.setDataFim(data2);
					
					
					turmaBOList.add(turmaBO);
				} while (registros.next());
				return turmaBOList;
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

	public int consultaPorTurmaAno(String turma, int ano) {
		Statement sentenca;
		ResultSet registros;
		int idTurma = 0;
		
		try {
			sentenca = con.createStatement();
			registros = sentenca
					.executeQuery("SELECT idTurma FROM turma " + 
							"WHERE turma = UPPER('" + turma + "') OR turma LIKE UPPER('%" + turma + "%') and ano = " + ano);
			registros.next();
			idTurma = Integer.parseInt(registros.getString("idTurma"));
		
			sentenca.close();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível carregar os dados!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		return idTurma;
	}
	
	public boolean incluir(TurmaBO turmaBO) {
		try {
			String sql = "INSERT INTO turma (idCurso, turma, ano, descricao, data_inicio, data_fim) VALUES (?,UPPER(?),?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, turmaBO.cursoBO.getId());
			stmt.setString(2, turmaBO.getTurma());
			stmt.setInt(3, turmaBO.getAno());
			stmt.setString(4, turmaBO.getDescricao());
			stmt.setDate(5, new Date(turmaBO.getDataInicio().getTimeInMillis()));
			stmt.setDate(6, new Date(turmaBO.getDataFim().getTimeInMillis()));
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
	
	public boolean alterar(TurmaBO turmaBO) {
		try {
			String sql = "UPDATE turma SET turma = UPPER(?), ano = ?, descricao = ?, idCurso = ?, data_inicio = ?, data_fim = ?"
					+ "WHERE idTurma = " + turmaBO.getId();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, turmaBO.getTurma());
			stmt.setInt(2, turmaBO.getAno());
			stmt.setString(3, turmaBO.getDescricao());
			stmt.setInt(4, turmaBO.cursoBO.getId());
			stmt.setDate(5, new Date(turmaBO.getDataInicio().getTimeInMillis()));
			stmt.setDate(6, new Date(turmaBO.getDataFim().getTimeInMillis()));
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

	public boolean excluir(int idTurma) {
		try {
			String sql = "DELETE FROM turma WHERE idTurma= " + idTurma;
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
