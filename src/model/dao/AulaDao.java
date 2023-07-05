package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import model.bo.AulaBO;
import model.bo.FrequenciaBO;
import model.exceptions.StringVaziaException;

public class AulaDao {
	Connection con;
	
	public AulaDao() {
		con = Conexao.conectaBanco();
	}
	
	public AulaBO consulta(int idTurma, Calendar dataAula) {
		Statement sentenca;
		ResultSet registros;
		AulaBO aulaBO = new AulaBO();
		try {
			sentenca = con.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			registros = sentenca.executeQuery("SELECT a.idAula, a.idTurma, dataAula, a.idColaborador, conteudo_ministrado, m.idMatricula, m.idAluno, nome, presenteManha, presenteTarde"
					+ " FROM aula a"
					+ " INNER JOIN frequencia f ON a.idAula = f.Idaula"
					+ " INNER JOIN contrato_matricula m ON m.idMatricula = f.idMatricula"
					+ " INNER JOIN aluno ON aluno.idAluno = m.idAluno"
					+ " WHERE a.idTurma = " + idTurma + " && dataAula = '" + sdf.format(dataAula.getTime()) + "'"
					+ " ORDER BY nome");
			if (registros.next()) {
				aulaBO.setIdAula(Integer.parseInt(registros.getString("idAula")));
				aulaBO.setIdTurma(Integer.parseInt(registros.getString("idTurma")));
				Calendar data = Calendar.getInstance();
				data.setTime(registros.getDate("dataAula"));
				aulaBO.setDataAula(data);
				aulaBO.setIdColaborador(Integer.parseInt(registros.getString("idColaborador")));
				aulaBO.setConteudo_ministrado(registros.getString("conteudo_ministrado"));
				
				do {
					FrequenciaBO frequenciaBO = new FrequenciaBO();
					frequenciaBO.matriculaBO.setIdMatricula(registros.getInt("idMatricula"));
					frequenciaBO.matriculaBO.alunoBO.setId(registros.getInt("idAluno"));
					try {
						frequenciaBO.matriculaBO.alunoBO.setNome(registros.getString("nome"));
					} catch (StringVaziaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					frequenciaBO.setPresenteManha(registros.getString("presenteManha"));
					frequenciaBO.setPresenteTarde(registros.getString("presenteTarde"));
					aulaBO.frequenciaBOList.add(frequenciaBO);
				}while (registros.next());
			}
			sentenca.close();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível carregar os dados!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
		return aulaBO;
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
			
			int idAula = consulta(aulaBO.getIdTurma(), aulaBO.getDataAula()).getIdAula();
			if(idAula > 0) {
				aulaBO.setIdAula(idAula);
				try {
					sql = "INSERT INTO frequencia (idAula, idMatricula, presenteManha, presenteTarde) VALUES (?,?,?,?)";
					stmt = con.prepareStatement(sql);
					int indice = 0;
					do {
						stmt.setInt(1, aulaBO.getIdAula());
						stmt.setInt(2, aulaBO.frequenciaBOList.get(indice).matriculaBO.getIdMatricula());
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
	
	public boolean alterar(AulaBO aulaBO) {
		try {
			String sql = "UPDATE aula SET idColaborador = ?, conteudo_ministrado = ?"
					+ " WHERE idAula = " + aulaBO.getIdAula();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, aulaBO.getIdColaborador());
			stmt.setString(2, aulaBO.getConteudo_ministrado());
			stmt.execute();
			
			//atualiza o registro de frequencia
			try {
				int indice = 0;
				do {
					sql = "UPDATE frequencia SET presenteManha = ?, presenteTarde = ?"
						+ " WHERE idAula = " + aulaBO.getIdAula()
						+ " && idMatricula = " + aulaBO.frequenciaBOList.get(indice).matriculaBO.getIdMatricula();
					stmt = con.prepareStatement(sql);
						stmt.setString(1, aulaBO.frequenciaBOList.get(indice).getPresenteManha());
						stmt.setString(2, aulaBO.frequenciaBOList.get(indice).getPresenteTarde());
						stmt.execute();
						indice++;
				} while (indice < aulaBO.frequenciaBOList.size());
			} catch (SQLException eSQL) {
				eSQL.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Não foi possível alterar o registro da frequencia!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
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
