package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.TurmaBO;
import model.exceptions.StringVaziaException;


public class TurmaDao {
	Connection con;
	public TurmaDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<TurmaBO> consultaPorCodigo(int codTurma) {
		ArrayList<TurmaBO> turmaBOList = consulta("codTurma = " + codTurma, "codTurma");
		return turmaBOList;
	}
	
	public ArrayList<TurmaBO> consultaPorTurma(String turma) {
		ArrayList<TurmaBO> turmaBOList = consulta("turma = UPPER('" + turma + "') OR turma LIKE UPPER('%" + turma + "%')", "turma");
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
			
					turmaBO.setCodigo(Integer.parseInt(registros.getString("codTurma")));
					turmaBO.setAno(Integer.parseInt(registros.getString("ano")));
					try {
						turmaBO.setTurma(registros.getString("turma"));
					} catch (StringVaziaException e) {
					}
					turmaBO.setDescricao(registros.getString("descricao"));
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

	public boolean incluir(TurmaBO turmaBO) {
		try {
			String sql = "INSERT INTO turma (turma, ano, descricao) VALUES (UPPER(?),?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, turmaBO.getTurma());
			stmt.setInt(2, turmaBO.getAno());
			stmt.setString(3, turmaBO.getDescricao());
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
			String sql = "UPDATE turma SET turma = UPPER(?), ano = ?, descricao = ?"
					+ "WHERE codTurma = " + turmaBO.getCodigo();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, turmaBO.getTurma());
			stmt.setInt(2, turmaBO.getAno());
			stmt.setString(3, turmaBO.getDescricao());
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

	public boolean excluir(int codTurma) {
		try {
			String sql = "DELETE FROM turma WHERE codTurma= " + codTurma;
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
