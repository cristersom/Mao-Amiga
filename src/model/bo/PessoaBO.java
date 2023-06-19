package model.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.exceptions.AnoInvalidoException;
import model.exceptions.CpfInvalidoException;
import model.exceptions.DiaInvalidoException;
import model.exceptions.MesInvalidoException;
import model.exceptions.StringVaziaException;
import view.Utils;

public class PessoaBO {
	private int codPessoa, numero;
	private String nome, cpf, nascimento, complemento; // endereço e bairro não são obrigatórios no caso de CEP geral
	public Calendar dataNascimento;
	public CepBO cep;

	public PessoaBO() {
		this.codPessoa = 0;
		this.cpf = "";
		this.nome = "";
		this.numero = 0;
		this.complemento = "";
		this.dataNascimento = Calendar.getInstance();
		this.cep = new CepBO();
	}

	public int getCodigo() {
		return codPessoa;
	}

	public void setCodigo(int codPessoa) {
		this.codPessoa = codPessoa;
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

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(int dia, int mes, int ano)
			throws DiaInvalidoException, MesInvalidoException, AnoInvalidoException {
		if (dia == 31 && mes != 1 && mes != 3 && mes != 5 && mes != 7 && mes != 8 && mes != 10 && mes != 12)
			throw new MesInvalidoException();

		else if (dia == 30 && mes == 2)
			throw new DiaInvalidoException();

		else if (dia == 29 && mes == 2 && ano % 4 != 0)
			throw new AnoInvalidoException();

		this.nascimento = String.valueOf(dia) + '/' + String.valueOf(mes) + '/' + String.valueOf(ano);
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
