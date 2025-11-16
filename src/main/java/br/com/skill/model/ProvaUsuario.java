package br.com.skill.model;

import java.time.LocalDate;

public class ProvaUsuario {
    
    private Integer idProvaUsuario;
    private Integer idProva;
    private Integer idUsuario;
    private Double notaObtida;
    private Double percentualAcerto;
    private String aprovado;
    private LocalDate dataRealizacao;
    
    public ProvaUsuario() {
    }
    
    public ProvaUsuario(Integer idProvaUsuario, Integer idProva, Integer idUsuario, 
                        Double notaObtida, Double percentualAcerto, String aprovado, 
                        LocalDate dataRealizacao) {
        this.idProvaUsuario = idProvaUsuario;
        this.idProva = idProva;
        this.idUsuario = idUsuario;
        this.notaObtida = notaObtida;
        this.percentualAcerto = percentualAcerto;
        this.aprovado = aprovado;
        this.dataRealizacao = dataRealizacao;
    }
    
    public Integer getIdProvaUsuario() {
        return idProvaUsuario;
    }
    
    public void setIdProvaUsuario(Integer idProvaUsuario) {
        this.idProvaUsuario = idProvaUsuario;
    }
    
    public Integer getIdProva() {
        return idProva;
    }
    
    public void setIdProva(Integer idProva) {
        this.idProva = idProva;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Double getNotaObtida() {
        return notaObtida;
    }
    
    public void setNotaObtida(Double notaObtida) {
        this.notaObtida = notaObtida;
    }
    
    public Double getPercentualAcerto() {
        return percentualAcerto;
    }
    
    public void setPercentualAcerto(Double percentualAcerto) {
        this.percentualAcerto = percentualAcerto;
    }
    
    public String getAprovado() {
        return aprovado;
    }
    
    public void setAprovado(String aprovado) {
        this.aprovado = aprovado;
    }
    
    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }
    
    public void setDataRealizacao(LocalDate dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
}

