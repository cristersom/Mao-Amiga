package model.bo;

import model.exceptions.StringVaziaException;

public class AlunoBO extends PessoaBO {
	private String nomeMae;
	
	public AlunoBO() {
		super();
		this.nomeMae = "";
	}
	
	public String getNomeMae() {
		return nomeMae;
	}
	
	public void setNomeMae(String nomeMae) throws StringVaziaException {
		if (nomeMae.trim().equals(""))
			throw new StringVaziaException();
		this.nomeMae = nomeMae;
	}
}
