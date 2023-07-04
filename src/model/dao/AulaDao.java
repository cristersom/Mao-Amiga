package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;
import model.bo.AulaBO;

public class AulaDao {
	Connection con;
	
	public AulaDao() {
		con = Conexao.conectaBanco();
	}
	
	public int getIdAula(int idTurma, Calendar dataAula) {
		Statement sentenca;
		ResultSet registros;
		int idAula = 0;
		
		try {
			sentenca = con.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			registros = sentenca.executeQuery("SELECT idAula FROM aula WHERE idTurma = " + idTurma + " && dataAula = '" + sdf.format(dataAula.getTime()) + "'");
			if (!registros.next()) {
				JOptionPane.showMessageDialog(null, "Nenhum registro de aula foi encontrado nesta data!", "Mensagem",
						JOptionPane.WARNING_MESSAGE);
			} else {
				idAula = Integer.parseInt(registros.getString("idAula"));
			}
			sentenca.close();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível carregar os dados!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		return idAula;
	}
	
	public boolean incluir(AulaBO aulaBO) {
		try {
			String sql = "INSERT INTO aula (idTurma, dataAula, idColaborador, conteudo_ministrado) VALUES (?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, aulaBO.getIdTurma());
			stmt.setDate(2, new Date(aulaBO.getDataAula().getTimeInMillis()));
			stmt.setInt(3, aulaBO.getIdColaborador());
			stmt.setString(4, aulaBO.getConteudo_ministrado());
			stmt.execute();
			// Conexao.desconectaBanco(con);
			
			int idAula = getIdAula(aulaBO.getIdTurma(), aulaBO.getDataAula());
			if(idAula > 0) {
				aulaBO.setIdAula(idAula);
				try {
					sql = "INSERT INTO frequencia (idAula, idMatricula, presenteManha, presenteTarde) VALUES (?,?,?,?)";
					stmt = con.prepareStatement(sql);
					int indice = 0;
					do {
						stmt.setInt(1, aulaBO.getIdAula());
						stmt.setInt(2, aulaBO.frequenciaBOList.get(indice).getIdMatricula());
						stmt.setString(3, aulaBO.frequenciaBOList.get(indice).getPresenteManha());
						stmt.setString(4, aulaBO.frequenciaBOList.get(indice).getPresenteTarde());
						stmt.execute();
						indice++;
					} while (indice < aulaBO.frequenciaBOList.size());

				} catch (SQLException eSQL) {
					eSQL.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Não foi possível realizar o registro da frequencia!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
					return false;
				}				
			} else {
				JOptionPane.showMessageDialog(null,
						"Não foi possível encontrar o registro da aula para registrar a frequencia!\n", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
						
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar o registro da aula!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}
