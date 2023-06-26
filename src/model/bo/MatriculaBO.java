package model.bo;

public class MatriculaBO {
	private int codMatricula, codTurma, codAluno;
	public TurmaBO turmaBO;
	
    public MatriculaBO() {
    	this.codMatricula = 0;
        this.codTurma = 0;
        this.codAluno = 0;
        this.turmaBO = new TurmaBO();
    }
    
    public int getMatricula() {
        return this.codMatricula;
    }
    
    public int getTurma() {
        return this.codTurma;
    }
    
    public int getAluno() {
        return this.codAluno;
    }
    
    public void setMatricula(int codMatricula) {
    	this.codMatricula = codMatricula;
    }
    
    public void setTurma(int codTurma) {
    	this.codTurma = codTurma;
    }
    public void setAluno(int codAluno) {
    	this.codAluno = codAluno;
    }
}
