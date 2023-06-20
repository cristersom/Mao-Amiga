package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.CursoBO;
import model.exceptions.StringVaziaException;
import view.FrameConsultaCurso;

public class CursoDao {
	Connection con;
	private FrameConsultaCurso pFormulario;

	public CursoDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<CursoBO> consultaPorCodigo(int codCurso) {
		ArrayList<CursoBO> cursoBOList = consulta("codCurso =" + codCurso, "codCurso");
		return cursoBOList;
	}

	public ArrayList<CursoBO> consultaPorCurso(String curso) {
		ArrayList<CursoBO> cursoBOList = consulta("curso = UPPER('" + curso + "') OR curso LIKE UPPER('%" + curso + "%')", "curso");
		return cursoBOList;
	}

	public ArrayList<CursoBO> consultaPorDescricao(String descricao) {
		ArrayList<CursoBO> cursoBOList = consulta("descricao LIKE '%" + descricao + "%'", "descricao");
		return cursoBOList;
	}

	private ArrayList<CursoBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery("SELECT * FROM curso WHERE " + sentencaSQL + " Order By " + ordem);

			if (registros.next()) {
				ArrayList<CursoBO> cursoBOList = new ArrayList<CursoBO>();
				do {
					CursoBO cursoBO = new CursoBO();

					cursoBO.setCodigo(Integer.parseInt(registros.getString("codcurso")));
					try {
						cursoBO.setCurso(registros.getString("curso"));
					} catch (StringVaziaException e) {
					}
					cursoBO.setDescricao(registros.getString("descricao"));
					cursoBOList.add(cursoBO);
				} while (registros.next());
				return cursoBOList;
			}
			sentenca.close();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(this.pFormulario,
					"Não foi possível carregar os dados!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	public boolean incluir(CursoBO cursoBO) {
		try {
			String sql = "INSERT INTO curso (curso, descricao) VALUES (UPPER(?),?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, cursoBO.getCurso());
			stmt.setString(2, cursoBO.getDescricao());
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(this.pFormulario,
					"Não foi possível realizar a inclusão!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean alterar(CursoBO cursoBO) {
		try {
			String sql = "UPDATE curso SET curso = UPPER(?), descricao = ?" + "WHERE codCurso = " + cursoBO.getCodigo();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, cursoBO.getCurso());
			stmt.setString(2, cursoBO.getDescricao());
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(this.pFormulario,
					"Não foi possível realizar a operação!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean excluir(int codCurso) {
		try {
			String sql = "DELETE FROM curso WHERE codCurso= " + codCurso;
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(this.pFormulario,
					"Não foi possível realizar a operação!\n"
							+ "Mensagem: Esse registro está sendo referenciado por outra tabela",
					"Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
