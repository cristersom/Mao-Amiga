package model.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.exceptions.CpfInvalidoException;
import model.exceptions.StringVaziaException;
import view.Utils;

public class PessoaBO {
	private int codPessoa, autorUsoImagem, numero;
	private String nome, cpf, complemento, nomeMae, celular, foneComercial, eMail, localTrabalho // endereço e bairro não são obrigatórios no caso de CEP geral
		  , rg, nomePai, nacionalidade, certNascimento, idiomaMaterno, tipo, sexo;
	public Calendar dataNascimento;
	public CepBO cep;

	public PessoaBO() {
		this.codPessoa = 0;
		this.tipo = "";
		this.autorUsoImagem = 0;
		this.dataNascimento = Calendar.getInstance();
		this.nome = "";
		this.cpf = "";
		this.nomeMae = "";
		this.rg = "";
		this.nomePai = "";
		this.nacionalidade = "";
		this.certNascimento = "";
		this.sexo = "";
		this.idiomaMaterno = "";
		
		this.cep = new CepBO();
		this.numero = 0;
		this.complemento = "";
		this.celular = "";
		this.foneComercial = "";
		this.eMail = "";
		this.localTrabalho = "";
	}

	public int getId() {
		return codPessoa;
	}

	public void setId(int codPessoa) {
		this.codPessoa = codPessoa;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getAutorUsoImagem() {
		return autorUsoImagem;
	}

	public void setAutorUsoImagem(int autorUsoImagem) {
		this.autorUsoImagem = autorUsoImagem;
	}
	
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws StringVaziaException {
		if (nome.trim().equals(""))
			throw new StringVaziaException();
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) throws StringVaziaException, CpfInvalidoException {
		if (cpf.trim().equals(""))
			throw new StringVaziaException();
		cpf = cpf.replaceAll("[^0-9]", "");
		Long cpfaux = Long.parseLong(cpf); // Isto, pois dependendo do CPF o valor se inteiro é maior que o suportado
		cpf = String.format("%011d", cpfaux);

		if (!validaCpf(cpf))
			throw new CpfInvalidoException();

		cpf = Utils.formatString(cpf, "#########-##");
		this.cpf = cpf;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) throws NumberFormatException {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public String getFoneComercial() {
		return foneComercial;
	}

	public void setFoneComercial(String foneComercial) {
		this.foneComercial = foneComercial;
	}
	
	public String getEmail() {
		return eMail;
	}

	public void setEmail(String eMail) {
		this.eMail = eMail;
	}
	
	public String getLocalTrabalho() {
		return localTrabalho;
	}

	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}
	
	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}
	
	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	
	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getCertNascimento() {
		return certNascimento;
	}

	public void setCertNascimento(String certNascimento) {
		this.certNascimento = certNascimento;
	}
	
	public String getIdiomaMaterno() {
		return idiomaMaterno;
	}

	public void setIdiomaMaterno(String idiomaMaterno) {
		this.idiomaMaterno = idiomaMaterno;
	}
	
	public Calendar getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // HH:mm:ss
		this.dataNascimento.setTime(sdf.parse(dataNascimento));
	}

	public String getNomeMae() {
		return nomeMae;
	}
	
	public void setNomeMae(String nomeMae) throws StringVaziaException {
		if (nomeMae.trim().equals(""))
			throw new StringVaziaException();
		this.nomeMae = nomeMae;
	}
	
	public boolean validaCpf(String cpf) {
		int i, resultado, soma, digito1, digito2;

		if (cpf.length() > 11)
			return false;
		soma = 0;
		for (i = 0; i < 9; i++)
			soma = soma + Integer.parseInt(cpf.substring(i, i + 1)) * (10 - i);

		resultado = soma % 11;

		if (resultado == 1 || resultado == 0)
			digito1 = 0;
		else
			digito1 = 11 - resultado;

		if (digito1 != Integer.parseInt(cpf.substring(9, 10)))
			return false;

		soma = 0;
		for (i = 0; i < 9; i++)
			soma = soma + Integer.parseInt(cpf.substring(i, i + 1)) * (11 - i);

		soma = soma + digito1 * 2;

		resultado = soma % 11;

		if (resultado == 1 || resultado == 0)
			digito2 = 0;
		else
			digito2 = 11 - resultado;

		if (digito2 != Integer.parseInt(cpf.substring(10, 11)))
			return false;

		return true;
	}

}
