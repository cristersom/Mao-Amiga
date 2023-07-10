package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.bo.FrequenciaBO;
import model.exceptions.StringVaziaException;

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
					"SELECT al.idAluno, nome, m.idMatricula, au.idTurma, COUNT(au.idTurma)*2 totalAulas"
					+ ", SUM(CASE WHEN presenteManha = 'SIM' THEN 0 ELSE 1 END) faltasManha"
					+ ", SUM(CASE WHEN presenteTarde = 'SIM' THEN 0 ELSE 1 END) faltasTarde"
					+ " FROM aula au INNER JOIN frequencia f ON au.idAula = f.idAula"
					+ " INNER JOIN contrato_matricula m ON m.idMatricula = f.idMatricula and m.idTurma = au.idTurma"
					+ " INNER JOIN aluno al ON al.idAluno = m.idAluno"
					+ " WHERE au.idTurma = " + idTurma
					+ " GROUP BY al.idAluno, nome, au.idTurma"
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
}
