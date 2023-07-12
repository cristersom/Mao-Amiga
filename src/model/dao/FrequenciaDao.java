package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;
import model.bo.FrequenciaBO;
import model.exceptions.StringVaziaException;
import view.Utils;

public class FrequenciaDao {
	Connection con;
	
	public FrequenciaDao() {
		con = Conexao.conectaBanco();
	}
	
	public ArrayList<FrequenciaBO> getFrequenciaTurma(int idTurma) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT al.idAluno, nome, m.idMatricula, m.idTurma"
					+ ", SUM(CASE WHEN presenteManha = 'Não' THEN 1 ELSE 0 END) faltasManha"
					+ ", SUM(CASE WHEN presenteTarde = 'Não' THEN 1 ELSE 0 END) faltasTarde"
					+ ", SUM(CASE WHEN f.idAula IS NULL THEN 0 ELSE 2 END) totalAulas"
					+ " FROM contrato_matricula m"
					+ " INNER JOIN aluno al ON al.idAluno = m.idAluno"
					+ " LEFT JOIN aula au ON au.idTurma = m.idTurma"
					+ " LEFT JOIN frequencia f ON au.idAula = f.idAula and f.idMatricula = m.idMatricula"
					+ " WHERE m.idTurma = " + idTurma
					+ " GROUP BY al.idAluno, nome, m.idTurma"
					+ " ORDER BY nome");

			if (registros.next()) {
				ArrayList<FrequenciaBO> frequenciaBOList = new ArrayList<FrequenciaBO>();
				do {
					FrequenciaBO frequenciaBO = new FrequenciaBO();

					frequenciaBO.matriculaBO.alunoBO.setId(Integer.parseInt(registros.getString("idAluno")));
					try {
						frequenciaBO.matriculaBO.alunoBO.setNome(registros.getString("nome"));
					} catch (StringVaziaException e1) {
					}
					frequenciaBO.matriculaBO.setIdMatricula(Integer.parseInt(registros.getString("idMatricula")));
					frequenciaBO.matriculaBO.setIdTurma(Integer.parseInt(registros.getString("idTurma")));
					frequenciaBO.setTotalAulas(Integer.parseInt(registros.getString("totalAulas")));
					frequenciaBO.setFaltasManha(Integer.parseInt(registros.getString("faltasManha")));
					frequenciaBO.setFaltasTarde(Integer.parseInt(registros.getString("faltasTarde")));

					frequenciaBOList.add(frequenciaBO);
				} while (registros.next());
				return frequenciaBOList;
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
	
	public Object[][][] getFrequenciaAluno(int idTurma, int idAluno) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery(
					"SELECT presenteManha, presenteTarde, dataAula"
					+ " FROM contrato_matricula m"
					+ " INNER JOIN aluno al ON al.idAluno = m.idAluno"
					+ " LEFT JOIN aula au ON au.idTurma = m.idTurma"
					+ " LEFT JOIN frequencia f ON au.idAula = f.idAula and f.idMatricula = m.idMatricula"
					+ " WHERE m.idTurma = " + idTurma
					+ " AND m.idAluno = " + idAluno
					+ " ORDER BY dataAula");

			if (registros.next()) {
				String meses[] = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outrubro","Novembro","Dezembro"};
				
		        Object[][][] vet = new Object[2][24][32];//turno;mes;dia
		        for(int i=0;i<24;i++)
		        	for(int j=1; j<32; j++) {
		        		vet[0][i][j] = false;
		        		vet[1][i][j] = false;
		        	}
		        for(int i=0; i<12; i++) {
		        	vet[0][i][0] = meses[i]+"(M)";
		        	vet[1][i][0] = meses[i]+"(T)";
		        }
		        
				do {
					
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataAula"));
					
					//Integer ano = data.get(Calendar.YEAR);
					Integer mes = data.get(Calendar.MONTH);
					Integer dia = data.get(Calendar.DAY_OF_MONTH);
					try {
						vet[0][mes][dia] = registros.getString("presenteManha").equals(Utils.Presente.Sim.toString()) ? true : false;
					}catch(NullPointerException e) {
						vet[0][mes][dia] = false;
					}
					
					try {
						vet[1][mes][dia] = registros.getString("presenteTarde").equals(Utils.Presente.Sim.toString()) ? true : false;
					}catch(NullPointerException e) {
						vet[1][mes][dia] = false;
					}
					
				} while (registros.next());
				return vet;
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
}
