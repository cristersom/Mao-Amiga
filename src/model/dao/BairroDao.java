package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.bo.BairroBO;
import model.exceptions.StringVaziaException;

public class BairroDao {
	Connection con;
	public BairroDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<BairroBO> consultaPorCodigo(int codBairro) {
		ArrayList<BairroBO> bairroBOList = consulta("codBairro =" + codBairro, "codBairro");
		return bairroBOList;
	}
	
	public ArrayList<BairroBO> consultaPorBairro(String bairro) {
		ArrayList<BairroBO> bairroBOList = consulta(
				"bairro LIKE UPPER('%" + bairro + "%') OR bairro LIKE '%" + bairro + "%'", "bairro");
		return bairroBOList;
	}	

	public ArrayList<BairroBO> consultaPorCidade(String cidade) {
		ArrayList<BairroBO> bairroBOList = consulta(
				"cidade LIKE UPPER('%" + cidade + "%') OR cidade LIKE '%" + cidade + "%'", "cidade");
		return bairroBOList;
	}


	public ArrayList<BairroBO> consultaPorUF(String uf) {
		ArrayList<BairroBO> bairroBOList = consulta("uf =  UPPER('" + uf + "')", "uf");
		return bairroBOList;
	}

	private ArrayList<BairroBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca
					.executeQuery("SELECT codBairro, bairro, cidade, bairro, uf, bairro.codcidade as codcidade "
							+ "FROM bairro INNER JOIN cidade ON bairro.codCidade = cidade.codCidade WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<BairroBO> bairroBOList = new ArrayList<BairroBO>();
				do {
					BairroBO bairroBO = new BairroBO();
					try {
						bairroBO.setBairro(registros.getString("bairro"));
						bairroBO.cidade.setCidade(registros.getString("cidade"));
					} catch (StringVaziaException ex) {
						
					}
					bairroBO.setCodigo(Integer.parseInt(registros.getString("codBairro")));
					bairroBO.cidade.setCodigo(Integer.parseInt(registros.getString("codcidade")));
					bairroBO.cidade.setUf(registros.getString("uf"));
					bairroBOList.add(bairroBO);
				} while (registros.next());
				return bairroBOList;
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

	public boolean incluir(BairroBO bairroBO) {
		try {
			String sql = "INSERT INTO bairro (bairro, codcidade) VALUES (UPPER(?),?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, bairroBO.getBairro());
			stmt.setInt(2, bairroBO.cidade.getCodigo());
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

	public boolean alterar(BairroBO bairroBO) {
		try {
			String sql = "UPDATE bairro SET bairro = UPPER(?), codcidade = ? WHERE codBairro = " + bairroBO.getCodigo();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, bairroBO.getBairro());
			stmt.setInt(2, bairroBO.cidade.getCodigo());
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

	public boolean excluir(int codBairro) {
		try {
			String sql = "DELETE FROM bairro WHERE codbairro= " + codBairro;
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