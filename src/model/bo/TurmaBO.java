package model.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import model.exceptions.StringVaziaException;

public class TurmaBO
{
    private int ano, idTurma;
    private String turma, descricao;
    private Calendar dataInicio, dataFim;
    public CursoBO cursoBO;
    public AlunoBO alunoBO;
    public ColaboradorBO colaboradorBO;
    public ResponsavelBO responsavelBO;
    
    public TurmaBO() {
        this.idTurma = 0;
        this.turma = "";
        this.ano = 1900;
        this.descricao = "";
        this.dataInicio = Calendar.getInstance();
        this.dataFim = Calendar.getInstance();
        this.cursoBO = new CursoBO();
        this.alunoBO = new AlunoBO();
        this.colaboradorBO = new ColaboradorBO();
        this.responsavelBO = new ResponsavelBO();
    }
    
    public int getId() {
        return this.idTurma;
    }
    
    public void setId(int idTurma) {
        this.idTurma = idTurma;
    }
    
    public int getAno() {
        return this.ano;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }
    
    public String getTurma() {
        return this.turma;
    }
    
    public void setTurma(String turma) throws StringVaziaException {
        if (turma.trim().equals("")) {
            throw new StringVaziaException();
        }
        this.turma = turma;
    }
    
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Calendar getDataInicio() {
    	return this.dataInicio;
    }
    
	public void setDataInicio(Calendar dataInicio)  {
		this.dataInicio = dataInicio;
	}
        
	public void setDataInicio(String dataInicio) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // HH:mm:ss
		this.dataInicio.setTime(sdf.parse(dataInicio));
	}
    
    public Calendar getDataFim() {
    	return this.dataFim;
    }
    
	public void setDataFim(Calendar dataFim)  {
		this.dataFim = dataFim;
	}
        
	public void setDataFim(String dataFim) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // HH:mm:ss
		this.dataFim.setTime(sdf.parse(dataFim));
	}
}