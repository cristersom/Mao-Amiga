package model.bo;

import model.exceptions.StringVaziaException;

public class CursoBO {
	private int codigo;
	private String curso, descricao;

	public CursoBO() {		
		codigo = 0;
		curso = "";
		descricao = "";
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) throws StringVaziaException {
		if (curso.trim().equals(""))
			throw new StringVaziaException();
		this.curso = curso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {		
		this.descricao = descricao;
	}
}
