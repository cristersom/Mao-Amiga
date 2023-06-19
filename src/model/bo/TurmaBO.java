package model.bo;

import model.exceptions.StringVaziaException;

public class TurmaBO
{
    private int ano;
    private int codTurma;
    private String turma;
    private String descricao;
    
    public TurmaBO() {
        this.codTurma = 0;
        this.turma = "";
        this.ano = 1900;
        this.descricao = "";
    }
    
    public int getCodigo() {
        return this.codTurma;
    }
    
    public void setCodigo(final int codTurma) {
        this.codTurma = codTurma;
    }
    
    public int getAno() {
        return this.ano;
    }
    
    public void setAno(final int ano) {
        this.ano = ano;
    }
    
    public String getTurma() {
        return this.turma;
    }
    
    public void setTurma(final String turma) throws StringVaziaException {
        if (turma.trim().equals("")) {
            throw new StringVaziaException();
        }
        this.turma = turma;
    }
    
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }
}