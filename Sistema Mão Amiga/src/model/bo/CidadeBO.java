package model.bo;

import model.exceptions.StringVaziaException;

public class CidadeBO {
	private int codigo;
	private String cidade, uf;

	public CidadeBO() {
		codigo = 0;
		cidade = "";
		uf = "";
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) throws StringVaziaException {
		if (cidade.trim().equals(""))
			throw new StringVaziaException();
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
