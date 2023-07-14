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

import model.bo.AnexoBO;
import view.FrameCadastroAluno;

public class AnexoDao {
	Connection con;
	private FrameCadastroAluno pFormulario;

	public AnexoDao() {
		con = Conexao.conectaBanco();
	}

	public ArrayList<AnexoBO> consultaPorIdAluno(int idAluno) {
		ArrayList<AnexoBO> anexoBOList = consulta("idAluno = " + idAluno, "nome");
		return anexoBOList;
	}
	
	private ArrayList<AnexoBO> consulta(String sentencaSQL, String ordem) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca.executeQuery("SELECT * FROM arquivo WHERE " + sentencaSQL + " Order By " + ordem);

			if (registros.next()) {
				ArrayList<AnexoBO> anexoBOList = new ArrayList<AnexoBO>();
				do {
					AnexoBO anexoBO = new AnexoBO();
					anexoBO.setIdArquivo(Integer.parseInt(registros.getString("idArquivo")));
					anexoBO.setIdAluno(Integer.parseInt(registros.getString("idAluno")));
					anexoBO.setNome(registros.getString("nome"));
					anexoBO.setDiretorio(registros.getString("diretorio"));
								
					Calendar data = Calendar.getInstance();
					data.setTime(registros.getDate("dataInclusao"));
					anexoBO.dataInclusao.setData(data);
			
					anexoBOList.add(anexoBO);
				} while (registros.next());
				return anexoBOList;
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

	public boolean incluir(AnexoBO anexoBO) {
		try {
			String sql = "INSERT INTO arquivo (idAluno, nome, dataInclusao, diretorio) VALUES (?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, anexoBO.getIdAluno());
			stmt.setString(2, anexoBO.getNome());
			stmt.setDate(3, new Date(anexoBO.dataInclusao.getData().getTimeInMillis()));
			stmt.setString(4, anexoBO.getDiretorio());
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(this.pFormulario,
					"Não foi possível realizar a inclusão!\nArquivo já existe!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public boolean excluir(int idArquivo, String diretorio) {
		try {
			String sql = "DELETE FROM arquivo WHERE idArquivo = " + idArquivo + " or diretorio like'%" + diretorio + "%'";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(this.pFormulario,
					"Não foi possível realizar a operação!\n","Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
