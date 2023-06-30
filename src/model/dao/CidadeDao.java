package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import model.bo.CidadeBO;
import model.exceptions.StringVaziaException;
import view.FrameConsultaCidade;

public class CidadeDao {
	Connection con;
	private FrameConsultaCidade pFormulario;

	public CidadeDao() {
		con = Conexao.conectaBanco();
	}

	public CidadeBO consultaPorCodigoUnico(int cod) {
		CidadeBO cidBO = new CidadeBO();
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();
			registros = sentenca.executeQuery("SELECT * FROM cidade WHERE idCidade =" + cod);
			if (!registros.next()) {
				JOptionPane.showMessageDialog(pFormulario, "Nenhum registro foi encontrado!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
				return cidBO;
			} else {
				cidBO.setId(Integer.parseInt(registros.getString("idCidade")));
				try {
					cidBO.setCidade(registros.getString("cidade"));
				} catch (StringVaziaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cidBO.setUf(registros.getString("uf"));
			}
			sentenca.close();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível carregar os dados!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}

		return cidBO;
	}

	public CidadeBO[] consultaPorCodigo(int cod) {
		CidadeBO cidBO[] = consulta("idCidade =" + cod, "idCidade");
		return cidBO;
	}

	public CidadeBO[] consultaPorCidade(String cidade) {
		CidadeBO cidBO[] = consulta("cidade LIKE UPPER('%" + cidade + "%') OR cidade LIKE '%" + cidade + "%'", "cidade");
		return cidBO;
	}

	public CidadeBO[] consultaPorUF(String uf) {
		CidadeBO cidBO[] = consulta("uf =  UPPER('" + uf + "') OR uf LIKE UPPER('%" + uf + "%')", "uf");
		return cidBO;
	}

	private CidadeBO[] consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros, registrosQtde = null;
		int numRegistros;

		try {
			sentenca = con.createStatement();

			// descobre o numero de registros
			registrosQtde = sentenca.executeQuery("SELECT COUNT(*) as numReg FROM cidade WHERE " + sentencaSQL);
			registrosQtde.next();
			numRegistros = Integer.parseInt(registrosQtde.getString("numReg"));

			// faz a consulta
			registros = sentenca.executeQuery("SELECT * FROM cidade WHERE " + sentencaSQL + " Order By " + ordem);

			if (registros.next()) {
				int i = 0;
				CidadeBO cidBO[] = new CidadeBO[numRegistros];
				do {
					cidBO[i] = new CidadeBO();
					cidBO[i].setId(Integer.parseInt(registros.getString("idCidade")));
					try {
						cidBO[i].setCidade(registros.getString("cidade"));
					} catch (StringVaziaException e) {
					}
					cidBO[i].setUf(registros.getString("uf"));
					i++;
				} while (registros.next());
				return cidBO;
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

	public boolean incluir(CidadeBO cidade) {
		Statement sentenca;
		try {
			sentenca = con.createStatement();
			String sentencaSQL = null;

			sentencaSQL = "INSERT INTO cidade (cidade, uf)" + "VALUES (UPPER('" + cidade.getCidade() + "'), '"
					+ cidade.getUf() + "') ";
			sentenca.executeUpdate(sentencaSQL);
			sentenca.close();
			// Conexao.desconectaBanco(con);
			return true;
		} 
        catch (SQLIntegrityConstraintViolationException eSQL) {
            try {
                sentenca =con.createStatement();
                ResultSet registro = sentenca.executeQuery("select max(idCidade) valor from cidade");
                registro.next();
                int valor = Integer.parseInt(registro.getString("valor")) + 1;
                sentenca.executeUpdate("alter table cidade AUTO_INCREMENT = " + String.valueOf(valor));
                sentenca.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            eSQL.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível realizar a operação!\nA cidade " + cidade.getCidade() + "/" + cidade.getUf() + " já existe.", "Erro", 0);
            return false;
        }		
		catch (SQLException eSQL2) {
			eSQL2.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a inclusão!\n" + "Mensagem: " + eSQL2.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean alterar(CidadeBO cidBO) {
		Statement sentenca;
		try {
			sentenca = con.createStatement();
			String sentencaSQL = null;
			sentencaSQL = "UPDATE cidade SET cidade = UPPER('" + cidBO.getCidade() + "'), uf = '" + cidBO.getUf()
					+ "' WHERE idCidade = " + cidBO.getId();

			sentenca.executeUpdate(sentencaSQL);
			sentenca.close();
		} 
        catch (SQLIntegrityConstraintViolationException eSQL) {
            eSQL.printStackTrace();
            JOptionPane.showMessageDialog(null, "Não foi possível realizar a operação!\nA cidade " + cidBO.getCidade() + "/" + cidBO.getUf() + " já existe.", "Erro", 0);
            return false;
        }	
		
		catch (SQLException eSQL2) {
			eSQL2.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a operação!\n" + "Mensagem: " + eSQL2.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean excluir(int idCidade) {
		Statement sentenca;
		try {
			sentenca = con.createStatement();
			String sentencaSQL = "DELETE FROM cidade WHERE idCidade= " + idCidade;
			sentenca.executeUpdate(sentencaSQL);
			sentenca.close();
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
