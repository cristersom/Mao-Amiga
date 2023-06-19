package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {

	public static Connection conectaBanco() {
		Connection conexao = null;
		try {
			//Conexão PostgreSql
			/*Class.forName("org.postgresql.Driver");
			
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
					"123456");*/
			
			//Conexão MySql
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_Sql", "root", "123456");
			//conexao = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/mao_amiga", "maoamiga", "123456789");
			
			
		} catch (SQLException eSQL) {
			// exceções de SQL
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na conexão com o banco!\n" + "Mensagem: " + eSQL.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			// demais exceções
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Falha na conexão com o banco!\n" + "Mensagem: " + e.getMessage(),
					"Erro", JOptionPane.ERROR_MESSAGE);
		}
		return conexao;
	}

	public static void desconectaBanco(Connection c) {
		try {
			c.close();
		} catch (SQLException eSQL) {
			// exceções de SQL
			eSQL.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Não foi possível desconectar o banco!\n" + "Mensagem: " + eSQL.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}