package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import model.bo.AutorizadoBO;
import view.FrameCadastroAluno;

public class AutorizadoDao {
	Connection con;
	private FrameCadastroAluno pFormulario;

	public AutorizadoDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<AutorizadoBO> consultaPorCodigo(int idAutorizado) {
		ArrayList<AutorizadoBO> autorizadoBOList = consulta("idAutorizado =" + idAutorizado, "idAutorizado");
		return autorizadoBOList;
	}

	public ArrayList<AutorizadoBO> consultaPorIdAluno(int idAluno) {
		ArrayList<AutorizadoBO> autorizadoBOList = consulta("aut.idAluno =" + idAluno, "aut.nome");
		return autorizadoBOList;
	}
	
	public ArrayList<AutorizadoBO> consultaPorNome(String nome) {
		ArrayList<AutorizadoBO> autorizadoBOList = consulta("aut.nome = UPPER('" + nome + "') OR aut.nome LIKE UPPER('%" + nome + "%')", "aut.nome");
		return autorizadoBOList;
	}
	
	public ArrayList<AutorizadoBO> consultaPorNomeAluno(String nome) {
		ArrayList<AutorizadoBO> autorizadoBOList = consulta("al.nome = UPPER('" + nome + "') OR al.nome LIKE UPPER('%" + nome + "%')", "al.nome, aut.nome");
		return autorizadoBOList;
	}

	public ArrayList<AutorizadoBO> consultaPorDescricao(String descricao) {
		ArrayList<AutorizadoBO> autorizadoBOList = consulta("descricao LIKE '%" + descricao + "%'", "descricao");
		return autorizadoBOList;
	}

	private ArrayList<AutorizadoBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery("SELECT idAutorizado, aut.idAluno, aut.nome, al.nome, aut.celular, aut.foneComercial, aut.tipo, dataInicio, dataFim"
					+ " FROM aluno_autorizados aut INNER JOIN aluno al ON aut.idAluno = al.idAluno"
					+ " WHERE " + sentencaSQL + " Order By " + ordem);

			if (registros.next()) {
				ArrayList<AutorizadoBO> autorizadoBOList = new ArrayList<AutorizadoBO>();
				do {
					AutorizadoBO autorizadoBO = new AutorizadoBO();

					autorizadoBO.setIdAutorizado(Integer.parseInt(registros.getString("idAutorizado")));
					autorizadoBO.setIdAluno(Integer.parseInt(registros.getString("idAluno")));
					autorizadoBO.setNome(registros.getString("aut.nome"));
					autorizadoBO.setNomeAluno(registros.getString("al.nome"));
					autorizadoBO.setCelular(registros.getString("celular"));
					autorizadoBO.setFoneComercial(registros.getString("foneComercial"));
					autorizadoBO.setTipo(registros.getString("tipo"));
					
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataInicio"));
					autorizadoBO.dataInicio.setData(data);
					
					Calendar data2 = Calendar.getInstance();
					data2.setTime(registros.getDate("dataFim"));
					autorizadoBO.dataFim.setData(data2);
					
					autorizadoBOList.add(autorizadoBO);
				} while (registros.next());
				return autorizadoBOList;
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

	public boolean incluir(AutorizadoBO autorizadoBO) {
		try {
			String sql = "INSERT INTO aluno_autorizados (idAluno, nome, celular, foneComercial, tipo, dataInicio, dataFim" + 
					") VALUES (?,UPPER(?),?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, autorizadoBO.getIdAluno());
			stmt.setString(2, autorizadoBO.getNome());
			stmt.setString(3, autorizadoBO.getCelular());
			stmt.setString(4, autorizadoBO.getFoneComercial());
			stmt.setString(5, autorizadoBO.getTipo());
			stmt.setDate(6, new Date(autorizadoBO.dataInicio.getData().getTimeInMillis()));
			stmt.setDate(7, new Date(autorizadoBO.dataFim.getData().getTimeInMillis()));
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

	public boolean alterar(AutorizadoBO autorizadoBO) {
		try {
			String sql = "UPDATE aluno_autorizados SET idAluno = ?, nome = UPPER(?), celular = ?, foneComercial = ?, tipo = ?, dataInicio = ?, dataFim = ?" + "WHERE idAutorizado = " + autorizadoBO.getIdAutorizado();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, autorizadoBO.getIdAluno());
			stmt.setString(2, autorizadoBO.getNome());
			stmt.setString(3, autorizadoBO.getCelular());
			stmt.setString(4, autorizadoBO.getFoneComercial());
			stmt.setString(5, autorizadoBO.getTipo());
			stmt.setDate(6, new Date(autorizadoBO.dataInicio.getData().getTimeInMillis()));
			stmt.setDate(7, new Date(autorizadoBO.dataFim.getData().getTimeInMillis()));
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

	public boolean excluir(int idAutorizado) {
		try {
			String sql = "DELETE FROM aluno_autorizados WHERE idAutorizado= " + idAutorizado;
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
