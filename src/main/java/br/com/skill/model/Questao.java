package br.com.skill.model;

public class Questao {
    
    private Integer idQuestao;
    private Integer idProva;
    private String enunciado;
    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;
    private String respostaCorreta;
    
    public Questao() {
    }
    
    public Questao(Integer idQuestao, Integer idProva, String enunciado, 
                   String alternativaA, String alternativaB, String alternativaC, 
                   String alternativaD, String respostaCorreta) {
        this.idQuestao = idQuestao;
        this.idProva = idProva;
        this.enunciado = enunciado;
        this.alternativaA = alternativaA;
        this.alternativaB = alternativaB;
        this.alternativaC = alternativaC;
        this.alternativaD = alternativaD;
        this.respostaCorreta = respostaCorreta;
    }
    
    public Integer getIdQuestao() {
        return idQuestao;
    }
    
    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }
    
    public Integer getIdProva() {
        return idProva;
    }
    
    public void setIdProva(Integer idProva) {
        this.idProva = idProva;
    }
    
    public String getEnunciado() {
        return enunciado;
    }
    
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
    
    public String getAlternativaA() {
        return alternativaA;
    }
    
    public void setAlternativaA(String alternativaA) {
        this.alternativaA = alternativaA;
    }
    
    public String getAlternativaB() {
        return alternativaB;
    }
    
    public void setAlternativaB(String alternativaB) {
        this.alternativaB = alternativaB;
    }
    
    public String getAlternativaC() {
        return alternativaC;
    }
    
    public void setAlternativaC(String alternativaC) {
        this.alternativaC = alternativaC;
    }
    
    public String getAlternativaD() {
        return alternativaD;
    }
    
    public void setAlternativaD(String alternativaD) {
        this.alternativaD = alternativaD;
    }
    
    public String getRespostaCorreta() {
        return respostaCorreta;
    }
    
    public void setRespostaCorreta(String respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }
}

