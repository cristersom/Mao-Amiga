package model.bo;

public class AlunoBO extends PessoaBO {
	private String fichaSaude, infoEducacao, observacoes;
	
	public AlunoBO() {
		super();
		this.fichaSaude = "";
		this.infoEducacao = "";
		this.observacoes = "";
	}
	
	public String getFichaSaude() {
		return fichaSaude;
	}

	public void setFichaSaude(String fichaSaude) {
		this.fichaSaude = fichaSaude;
	}
	
	public String getInfoEducacao() {
		return infoEducacao;
	}

	public void setInfoEducacao(String infoEducacao) {
		this.infoEducacao = infoEducacao;
	}
	
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
}
