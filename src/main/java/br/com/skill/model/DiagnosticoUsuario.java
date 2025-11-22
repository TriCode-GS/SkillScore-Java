package br.com.skill.model;

import java.time.LocalDate;

public class DiagnosticoUsuario {
    
    private Integer idDiagnostico;
    private Integer idUsuario;
    private Integer idTrilha;
    private Integer pontuacaoAdmin;
    private Integer pontuacaoTech;
    private Integer pontuacaoRh;
    private LocalDate dataRealizacao;
    
    public DiagnosticoUsuario() {
    }
    
    public DiagnosticoUsuario(Integer idDiagnostico, Integer idUsuario, Integer idTrilha, 
                              Integer pontuacaoAdmin, Integer pontuacaoTech, Integer pontuacaoRh, 
                              LocalDate dataRealizacao) {
        this.idDiagnostico = idDiagnostico;
        this.idUsuario = idUsuario;
        this.idTrilha = idTrilha;
        this.pontuacaoAdmin = pontuacaoAdmin;
        this.pontuacaoTech = pontuacaoTech;
        this.pontuacaoRh = pontuacaoRh;
        this.dataRealizacao = dataRealizacao;
    }
    
    public Integer getIdDiagnostico() {
        return idDiagnostico;
    }
    
    public void setIdDiagnostico(Integer idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Integer getIdTrilha() {
        return idTrilha;
    }
    
    public void setIdTrilha(Integer idTrilha) {
        this.idTrilha = idTrilha;
    }
    
    public Integer getPontuacaoAdmin() {
        return pontuacaoAdmin;
    }
    
    public void setPontuacaoAdmin(Integer pontuacaoAdmin) {
        this.pontuacaoAdmin = pontuacaoAdmin;
    }
    
    public Integer getPontuacaoTech() {
        return pontuacaoTech;
    }
    
    public void setPontuacaoTech(Integer pontuacaoTech) {
        this.pontuacaoTech = pontuacaoTech;
    }
    
    public Integer getPontuacaoRh() {
        return pontuacaoRh;
    }
    
    public void setPontuacaoRh(Integer pontuacaoRh) {
        this.pontuacaoRh = pontuacaoRh;
    }
    
    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }
    
    public void setDataRealizacao(LocalDate dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
}

