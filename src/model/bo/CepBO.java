package model.bo;

import model.exceptions.CepInvalidoException;
import model.exceptions.StringVaziaException;
import view.Utils;

public class CepBO {
	private int idCep;
	private String CEP, logradouro, bairro; // endereço e bairro não são obrigatórios no caso de CEP geral
	public CidadeBO cidade;

	public CepBO() {
		idCep = 0;
		CEP = "";
		logradouro = "";
		bairro = "";
		cidade = new CidadeBO();
	}

	public int getId() {
		return idCep;
	}

	public void setId(int idCep) {
		this.idCep = idCep;
	}

	public String getCep() {
		return CEP;
	}

	public void setCep(String CEP) throws StringVaziaException, CepInvalidoException {
		if (CEP.trim().equals(""))
			throw new StringVaziaException();

		CEP = CEP.replaceAll("[^0-9]", "");

		if (CEP.length() > 8)
			throw new CepInvalidoException();

		CEP = String.format("%08d", Integer.parseInt(CEP));
		CEP = Utils.formatString(CEP, "#####-###");

		this.CEP = CEP;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

}
