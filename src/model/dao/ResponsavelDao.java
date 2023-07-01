package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import javax.swing.JOptionPane;

import model.bo.ResponsavelBO;
import model.bo.TurmaBO;
import model.exceptions.CepInvalidoException;
import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;

public class ResponsavelDao {
	Connection con;

	public ResponsavelDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<ResponsavelBO> consultaPorCodigo(int idResponsavel) {
		ArrayList<ResponsavelBO> responsavelBOList = consulta("idResponsavel = " + idResponsavel, "idResponsavel");
		return responsavelBOList;
	}

	public ArrayList<ResponsavelBO> consultaPorNome(String nome) {
		ArrayList<ResponsavelBO> responsavelBOList = consulta("nome LIKE '%" + nome + "%'", "nome");
		return responsavelBOList;
	}

	public ArrayList<ResponsavelBO> consultaPorCpf(String cpf) {
		ArrayList<ResponsavelBO> responsavelBOList = consulta("cpf = '" + cpf + "'", "cpf");
		return responsavelBOList;
	}

	private ArrayList<ResponsavelBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT idResponsavel, cpf, nome, rg, certNascimento, sexo, nomeMae, nomePai, nacionalidade, dataNascimento, celular, foneComercial"
					+ ", eMail, localTrabalho, tipo, autorUsoImagem, responsavel.idCep, numero, complemento, cep, logradouro, bairro, cep.idCidade, cidade, uf "
							+ "FROM responsavel INNER JOIN cep ON responsavel.idCep = cep.idCep "
							+ "INNER JOIN cidade ON cep.idCidade = cidade.idCidade" + " WHERE " + sentencaSQL
							+ " Order By " + ordem);

			if (registros.next()) {
				ArrayList<ResponsavelBO> responsavelBOList = new ArrayList<ResponsavelBO>();
				do {
					ResponsavelBO responsavelBO = new ResponsavelBO();

					responsavelBO.setId(Integer.parseInt(registros.getString("idResponsavel")));
					responsavelBO.setAutorUsoImagem(Integer.parseInt(registros.getString("autorUsoImagem")));
					
					try {
						responsavelBO.setCpf(registros.getString("cpf"));
						responsavelBO.setNome(registros.getString("nome"));
						responsavelBO.setNomeMae(registros.getString("nomeMae"));
					} catch (StringVaziaException | CpfInvalidoException e1) {
					}
					responsavelBO.setRg(registros.getString("rg"));
					responsavelBO.setCertNascimento(registros.getString("certNascimento"));
					responsavelBO.setSexo(registros.getString("sexo"));
					responsavelBO.setNomePai(registros.getString("nomePai"));
					responsavelBO.setNacionalidade(registros.getString("nacionalidade"));
					responsavelBO.setCelular(registros.getString("celular"));
					responsavelBO.setFoneComercial(registros.getString("foneComercial"));
					responsavelBO.setEmail(registros.getString("eMail"));
					responsavelBO.setLocalTrabalho(registros.getString("localTrabalho"));
					responsavelBO.setTipo(registros.getString("tipo"));

					
					responsavelBO.cep.setId(Integer.parseInt(registros.getString("idCep")));
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataNascimento"));
					responsavelBO.setDataNascimento(data);
					responsavelBO.setComplemento(registros.getString("complemento"));
					try {
						responsavelBO.cep.setCep(registros.getString("cep"));
						responsavelBO.cep.cidade.setCidade(registros.getString("cidade"));
						responsavelBO.setNumero(Integer.parseInt(registros.getString("numero")));
					} catch (StringVaziaException | CepInvalidoException e) {
					}
					responsavelBO.cep.setLogradouro(registros.getString("logradouro"));
					responsavelBO.cep.setBairro(registros.getString("bairro"));
					responsavelBO.cep.cidade.setId(Integer.parseInt(registros.getString("idCidade")));
					responsavelBO.cep.cidade.setUf(registros.getString("uf"));

					responsavelBOList.add(responsavelBO);
				} while (registros.next());
				return responsavelBOList;
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

	public boolean incluir(ResponsavelBO responsavelBO) {
		try {
			String sql = "INSERT INTO responsavel (cpf, nome, rg, certNascimento, sexo, nomeMae, nomePai, nacionalidade"
					+ ", dataNascimento, idCep, numero, complemento, celular, foneComercial, eMail, localTrabalho, tipo, autorUsoImagem)"
					+ "VALUES (?, UPPER(?), ?, ?, ?, UPPER(?), UPPER(?), UPPER(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, responsavelBO.getCpf());
			stmt.setString(2, responsavelBO.getNome());
			stmt.setString(3, responsavelBO.getRg());
			stmt.setString(4, responsavelBO.getCertNascimento());
			stmt.setString(5, responsavelBO.getSexo());
			stmt.setString(6, responsavelBO.getNomeMae());
			stmt.setString(7, responsavelBO.getNomePai());
			stmt.setString(8, responsavelBO.getNacionalidade());
			stmt.setDate(9, new Date(responsavelBO.getDataNascimento().getTimeInMillis()));
			stmt.setInt(10, responsavelBO.cep.getId());
			stmt.setInt(11, responsavelBO.getNumero());
			stmt.setString(12, responsavelBO.getComplemento());
			stmt.setString(13, responsavelBO.getCelular());
			stmt.setString(14, responsavelBO.getFoneComercial());
			stmt.setString(15, responsavelBO.getEmail());
			stmt.setString(16, responsavelBO.getLocalTrabalho());
			stmt.setString(17, responsavelBO.getTipo());
			stmt.setInt(18, responsavelBO.getAutorUsoImagem());
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

	public boolean alterar(ResponsavelBO responsavelBO) {
		try {
			
			String sql = "UPDATE responsavel SET cpf = ?, nome = UPPER(?), rg = ?, certNascimento = ?, sexo = ?, nomeMae = UPPER(?), nomePai = UPPER(?), nacionalidade = UPPER(?)"
					+ ", dataNascimento = ?, idCep = ?, numero = ?, complemento = ?, celular = ?, foneComercial = ?, eMail = ?, localTrabalho = ?, tipo = ?, autorUsoImagem = ?"
					+ " WHERE idResponsavel = " + responsavelBO.getId();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, responsavelBO.getCpf());
			stmt.setString(2, responsavelBO.getNome());
			stmt.setString(3, responsavelBO.getRg());
			stmt.setString(4, responsavelBO.getCertNascimento());
			stmt.setString(5, responsavelBO.getSexo());
			stmt.setString(6, responsavelBO.getNomeMae());
			stmt.setString(7, responsavelBO.getNomePai());
			stmt.setString(8, responsavelBO.getNacionalidade());
			stmt.setDate(9, new Date(responsavelBO.getDataNascimento().getTimeInMillis()));
			stmt.setInt(10, responsavelBO.cep.getId());
			stmt.setInt(11, responsavelBO.getNumero());
			stmt.setString(12, responsavelBO.getComplemento());
			stmt.setString(13, responsavelBO.getCelular());
			stmt.setString(14, responsavelBO.getFoneComercial());
			stmt.setString(15, responsavelBO.getEmail());
			stmt.setString(16, responsavelBO.getLocalTrabalho());
			stmt.setString(17, responsavelBO.getTipo());
			stmt.setInt(18, responsavelBO.getAutorUsoImagem());
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

	public boolean excluir(int idResponsavel) {
		try {
			String sql = "DELETE FROM responsavel WHERE idResponsavel= " + idResponsavel;
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
