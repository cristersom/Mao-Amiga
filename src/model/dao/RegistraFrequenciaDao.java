package model.dao;

import java.sql.Connection;

public class RegistraFrequenciaDao {
	Connection con;
	
	public RegistraFrequenciaDao() {
		con = Conexao.conectaBanco();
	}
	
	
}
