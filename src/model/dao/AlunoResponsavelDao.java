package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.bo.AlunoResponsavelBO;
import model.exceptions.StringVaziaException;

public class AlunoResponsavelDao {
	Connection con;
	public AlunoResponsavelDao() {
		con = Conexao.conectaBanco();
	}
	
	public ArrayList<AlunoResponsavelBO> consulta(int idMatricula) {
		Statement sentenca;
		ResultSet registros;

		try {
			sentenca = con.createStatement();

			// faz a consulta
			registros = sentenca
					.executeQuery("SELECT idResponsavelAluno, responsavel.idResponsavel, responsavel.nome, aluno.nome, responsavel.tipo, responsavel.celular, responsavel.foneComercial  "
							+ " FROM aluno_responsaveis"
							+ " INNER JOIN contrato_matricula ON contrato_matricula.idMatricula = aluno_responsaveis.idMatricula"
							+ " INNER JOIN aluno ON aluno.idAluno = contrato_matricula.idAluno"
							+ " INNER JOIN responsavel ON responsavel.idResponsavel = aluno_responsaveis.idResponsavel"
							+ " WHERE aluno_responsaveis.idMatricula = " + idMatricula
							+ " Order By 2");

			if (registros.next()) {
				ArrayList<AlunoResponsavelBO> alunoResponsavelBOList = new ArrayList<AlunoResponsavelBO>();
				do {
					AlunoResponsavelBO alunoResponsavelBO = new AlunoResponsavelBO();
					
					alunoResponsavelBO.setIdMatricula(idMatricula);
					alunoResponsavelBO.setIdResponsavelAluno(Integer.parseInt(registros.getString("idResponsavelAluno")));
					alunoResponsavelBO.responsavelBO.setId(Integer.parseInt(registros.getString("idResponsavel")));
					try {
						alunoResponsavelBO.responsavelBO.setNome(registros.getString("responsavel.nome"));
						alunoResponsavelBO.alunoBO.setNome(registros.getString("aluno.nome"));
					} catch (StringVaziaException e) {}
					alunoResponsavelBO.responsavelBO.setTipo(registros.getString("tipo"));
					alunoResponsavelBO.responsavelBO.setCelular(registros.getString("celular"));
					alunoResponsavelBO.responsavelBO.setFoneComercial(registros.getString("foneComercial"));
					
					alunoResponsavelBOList.add(alunoResponsavelBO);
				} while (registros.next());
				return alunoResponsavelBOList;
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
	
	public boolean incluir(int idMatricula, int idResponsavel) {
		try {
			String sql = "INSERT INTO aluno_responsaveis (idMatricula, idResponsavel) VALUES (?,?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idMatricula);
			stmt.setInt(2, idResponsavel);
			stmt.execute();
			// Conexao.desconectaBanco(con);
			return true;
		} catch (SQLException eSQL) {
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível realizar a inclusão!\n Responsável já cadastrado para o respectivo aluno e turma.\n"
							+ "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public boolean excluir(int idResponsavelAluno) {
		try {
			String sql = "DELETE FROM aluno_responsaveis WHERE idResponsavelAluno= " + idResponsavelAluno;
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
