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

import model.bo.ColaboradorBO;
import model.exceptions.CepInvalidoException;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;

public class ColaboradorDao {
	Connection con;

	public ColaboradorDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<ColaboradorBO> consultaPorCodigo(int codColaborador) {
		ArrayList<ColaboradorBO> colaboradorBOList = consulta("codColaborador = " + codColaborador, "codColaborador");
		return colaboradorBOList;
	}

	public ArrayList<ColaboradorBO> consultaPorNome(String nome) {
		ArrayList<ColaboradorBO> colaboradorBOList = consulta("nome LIKE '%" + nome + "%'", "nome");
		return colaboradorBOList;
	}

	public ArrayList<ColaboradorBO> consultaPorCpf(String cpf) {
		ArrayList<ColaboradorBO> colaboradorBOList = consulta("cpf = '" + cpf + "'", "cpf");
		return colaboradorBOList;
	}

	private ArrayList<ColaboradorBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT codColaborador, cpf, nome, rg, certNascimento, sexo, nomeMae, nomePai, nacionalidade, dataNascimento, celular, foneComercial"
					+ ", eMail, tipo, autorUsoImagem, colaborador.codCep, numero, complemento, cep, logradouro, bairro, cep.codCidade, cidade, uf "
							+ "FROM colaborador INNER JOIN cep ON colaborador.codCep = cep.codCep "
							+ "INNER JOIN cidade ON cep.codCidade = cidade.codCidade" + " WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<ColaboradorBO> colaboradorBOList = new ArrayList<ColaboradorBO>();
				do {
					ColaboradorBO colaboradorBO = new ColaboradorBO();

					colaboradorBO.setCodigo(Integer.parseInt(registros.getString("codColaborador")));
					colaboradorBO.setAutorUsoImagem(Integer.parseInt(registros.getString("autorUsoImagem")));
					
					try {
						colaboradorBO.setCpf(registros.getString("cpf"));
						colaboradorBO.setNome(registros.getString("nome"));
						colaboradorBO.setNomeMae(registros.getString("nomeMae"));
					} catch (StringVaziaException | CpfInvalidoException e1) {
					}
					colaboradorBO.setRg(registros.getString("rg"));
					colaboradorBO.setCertNascimento(registros.getString("certNascimento"));
					colaboradorBO.setSexo(registros.getString("sexo"));
					colaboradorBO.setNomePai(registros.getString("nomePai"));
					colaboradorBO.setNacionalidade(registros.getString("nacionalidade"));
					colaboradorBO.setCelular(registros.getString("celular"));
					colaboradorBO.setFoneComercial(registros.getString("foneComercial"));
					colaboradorBO.setEmail(registros.getString("eMail"));
					colaboradorBO.setTipo(registros.getString("tipo"));

					
					colaboradorBO.cep.setCodigo(Integer.parseInt(registros.getString("codCep")));
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataNascimento"));
					colaboradorBO.setDataNascimento(data);
					colaboradorBO.setComplemento(registros.getString("complemento"));
					try {
						colaboradorBO.cep.setCep(registros.getString("cep"));
						colaboradorBO.cep.cidade.setCidade(registros.getString("cidade"));
						colaboradorBO.setNumero(Integer.parseInt(registros.getString("numero")));
					} catch (StringVaziaException | CepInvalidoException e) {
					}
					colaboradorBO.cep.setLogradouro(registros.getString("logradouro"));
					colaboradorBO.cep.setBairro(registros.getString("bairro"));
					colaboradorBO.cep.cidade.setCodigo(Integer.parseInt(registros.getString("codCidade")));
					colaboradorBO.cep.cidade.setUf(registros.getString("uf"));

					colaboradorBOList.add(colaboradorBO);
				} while (registros.next());
				return colaboradorBOList;
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

	public boolean incluir(ColaboradorBO colaboradorBO) {
		try {
			String sql = "INSERT INTO colaborador (cpf, nome, rg, certNascimento, sexo, nomeMae, nomePai, nacionalidade"
					+ ", dataNascimento, codCep, numero, complemento, celular, foneComercial, eMail, tipo, autorUsoImagem)"
					+ "VALUES (?, UPPER(?), ?, ?, ?, UPPER(?), UPPER(?), UPPER(?), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, colaboradorBO.getCpf());
			stmt.setString(2, colaboradorBO.getNome());
			stmt.setString(3, colaboradorBO.getRg());
			stmt.setString(4, colaboradorBO.getCertNascimento());
			stmt.setString(5, colaboradorBO.getSexo());
			stmt.setString(6, colaboradorBO.getNomeMae());
			stmt.setString(7, colaboradorBO.getNomePai());
			stmt.setString(8, colaboradorBO.getNacionalidade());
			stmt.setDate(9, new Date(colaboradorBO.getDataNascimento().getTimeInMillis()));
			stmt.setInt(10, colaboradorBO.cep.getCodigo());
			stmt.setInt(11, colaboradorBO.getNumero());
			stmt.setString(12, colaboradorBO.getComplemento());
			stmt.setString(13, colaboradorBO.getCelular());
			stmt.setString(14, colaboradorBO.getFoneComercial());
			stmt.setString(15, colaboradorBO.getEmail());
			stmt.setString(16, colaboradorBO.getTipo());
			stmt.setInt(17, colaboradorBO.getAutorUsoImagem());
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

	public boolean alterar(ColaboradorBO colaboradorBO) {
		try {
			
			String sql = "UPDATE colaborador SET cpf = ?, nome = UPPER(?), rg = ?, certNascimento = ?, sexo = ?, nomeMae = UPPER(?), nomePai = UPPER(?), nacionalidade = UPPER(?)"
					+ ", dataNascimento = ?, codCep = ?, numero = ?, complemento = ?, celular = ?, foneComercial = ?, eMail = ?, tipo = ?, autorUsoImagem = ?"
					+ " WHERE codColaborador = " + colaboradorBO.getCodigo();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, colaboradorBO.getCpf());
			stmt.setString(2, colaboradorBO.getNome());
			stmt.setString(3, colaboradorBO.getRg());
			stmt.setString(4, colaboradorBO.getCertNascimento());
			stmt.setString(5, colaboradorBO.getSexo());
			stmt.setString(6, colaboradorBO.getNomeMae());
			stmt.setString(7, colaboradorBO.getNomePai());
			stmt.setString(8, colaboradorBO.getNacionalidade());
			stmt.setDate(9, new Date(colaboradorBO.getDataNascimento().getTimeInMillis()));
			stmt.setInt(10, colaboradorBO.cep.getCodigo());
			stmt.setInt(11, colaboradorBO.getNumero());
			stmt.setString(12, colaboradorBO.getComplemento());
			stmt.setString(13, colaboradorBO.getCelular());
			stmt.setString(14, colaboradorBO.getFoneComercial());
			stmt.setString(15, colaboradorBO.getEmail());
			stmt.setString(16, colaboradorBO.getTipo());
			stmt.setInt(17, colaboradorBO.getAutorUsoImagem());
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

	public boolean excluir(int codColaborador) {
		try {
			String sql = "DELETE FROM colaborador WHERE codColaborador= " + codColaborador;
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
