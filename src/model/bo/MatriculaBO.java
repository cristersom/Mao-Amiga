package model.bo;

public class MatriculaBO {
	private int idMatricula, idTurma, idAluno;
	public TurmaBO turmaBO;
	
    public MatriculaBO() {
    	this.idMatricula = 0;
        this.idTurma = 0;
        this.idAluno = 0;
        this.turmaBO = new TurmaBO();
    }
    
    public int getMatricula() {
        return this.idMatricula;
    }
    
    public int getTurma() {
        return this.idTurma;
    }
    
    public int getAluno() {
        return this.idAluno;
    }
    
    public void setMatricula(int idMatricula) {
    	this.idMatricula = idMatricula;
    }
    
    public void setTurma(int idTurma) {
    	this.idTurma = idTurma;
    }
    public void setAluno(int idAluno) {
    	this.idAluno = idAluno;
    }
}
