package model.bo;

import model.exceptions.StringVaziaException;

public class BairroBO
{
    private int codigo;
    private String bairro;
    public CidadeBO cidade;
    
    public BairroBO() {
        this.codigo = 0;
        this.bairro = "";
        this.cidade = new CidadeBO();
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(final int codigo) {
        this.codigo = codigo;
    }
    
    public String getBairro() {
        return this.bairro;
    }
    
    public void setBairro(final String bairro) throws StringVaziaException {
        if (bairro.trim().equals("")) {
            throw new StringVaziaException();
        }
        this.bairro = bairro;
    }
}