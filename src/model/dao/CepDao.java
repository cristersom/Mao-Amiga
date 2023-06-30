package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.CepBO;
import model.exceptions.CepInvalidoException;
import model.exceptions.StringVaziaException;

public class CepDao {
	Connection con;
	public CepDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<CepBO> consultaPorCep(String cep) {
		ArrayList<CepBO> cepBOList = consulta("cep = '" + cep + "'", "cep");
		return cepBOList;
	}
	
	public ArrayList<CepBO> consultaPorCodigo(int idCep) {
		ArrayList<CepBO> cepBOList = consulta("idCep =" + idCep, "idCep");
		return cepBOList;
	}

	public ArrayList<CepBO> consultaPorCidade(String cidade) {
		ArrayList<CepBO> cepBOList = consulta(
				"cidade LIKE UPPER('%" + cidade + "%') OR cidade LIKE '%" + cidade + "%'", "cidade");
		return cepBOList;
	}

	public ArrayList<CepBO> consultaPorLogradouro(String logradouro) {
		ArrayList<CepBO> cepBOList = consulta(
				"logradouro LIKE UPPER('%" + logradouro + "%') OR logradouro LIKE '%" + logradouro + "%'", "logradouro");
		return cepBOList;
	}

	public ArrayList<CepBO> consultaPorBairro(String bairro) {
		ArrayList<CepBO> cepBOList = consulta(
				"bairro LIKE UPPER('%" + bairro + "%') OR bairro LIKE '%" + bairro + "%'", "bairro");
		return cepBOList;
	}

	public ArrayList<CepBO> consultaPorUF(String uf) {
		ArrayList<CepBO> cepBOList = consulta("uf =  UPPER('" + uf + "')", "uf");
		return cepBOList;
	}

	private ArrayList<CepBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca
					.executeQuery("SELECT idCep, cep, cidade, logradouro, bairro, uf, cep.idCidade "
							+ "FROM cep INNER JOIN cidade ON cep.idCidade = cidade.idCidade WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<CepBO> cepBOList = new ArrayList<CepBO>();
				do {
					CepBO cepBO = new CepBO();
					try {
						cepBO.setCep(registros.getString("cep"));
						cepBO.cidade.setCidade(registros.getString("cidade"));
					} catch (StringVaziaException | CepInvalidoException e1) {
					}
					cepBO.setId(Integer.parseInt(registros.getString("idCep")));
					cepBO.setLogradouro(registros.getString("logradouro"));
					cepBO.setBairro(registros.getString("bairro"));
					cepBO.cidade.setId(Integer.parseInt(registros.getString("idCidade")));
					cepBO.cidade.setUf(registros.getString("uf"));
					cepBOList.add(cepBO);
				} while (registros.next());
				return cepBOList;
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

	public boolean incluir(CepBO cepBO) {
		try {
			String sql = "INSERT INTO cep (cep, logradouro, bairro, idCidade) VALUES (?,UPPER(?),UPPER(?),?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, cepBO.getCep());
			stmt.setString(2, cepBO.getLogradouro());
			stmt.setString(3, cepBO.getBairro());
			stmt.setInt(4, cepBO.cidade.getId());
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

	public boolean alterar(CepBO cepBO) {
		try {
			String sql = "UPDATE cep SET cep = ?, logradouro = UPPER(?), bairro = UPPER(?), idCidade = ? "
					+ "WHERE idCep = " + cepBO.getId();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, cepBO.getCep());
			stmt.setString(2, cepBO.getLogradouro());
			stmt.setString(3, cepBO.getBairro());
			stmt.setInt(4, cepBO.cidade.getId());
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possíver realizar a operação!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean excluir(int idCep) {
		try {
			String sql = "DELETE FROM cep WHERE idCep= " + idCep;
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