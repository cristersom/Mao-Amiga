package model.bo;

public class MatriculaBO {
	private int idMatricula, idTurma;


	public AlunoBO alunoBO;
	
    public MatriculaBO() {
    	this.idMatricula = 0;
    	this.idTurma = 0;
    	this.alunoBO = new AlunoBO();
    }
    
    public int getMatricula() {
        return this.idMatricula;
    }
       
    public void setMatricula(int idMatricula) {
    	this.idMatricula = idMatricula;
    }
    
	public int getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
}
