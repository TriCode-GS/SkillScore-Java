package br.com.skill.model;

import java.time.LocalDate;

public class TrilhaCurso {
    
    private Integer idTrilhaCurso;
    private Integer idTrilha;
    private Integer idCurso;
    private Integer ordemFase;
    private String statusFase;
    private LocalDate dataConclusao;
    
    public TrilhaCurso() {
    }
    
    public TrilhaCurso(Integer idTrilhaCurso, Integer idTrilha, Integer idCurso, Integer ordemFase, 
                       String statusFase, LocalDate dataConclusao) {
        this.idTrilhaCurso = idTrilhaCurso;
        this.idTrilha = idTrilha;
        this.idCurso = idCurso;
        this.ordemFase = ordemFase;
        this.statusFase = statusFase;
        this.dataConclusao = dataConclusao;
    }
    
    public Integer getIdTrilhaCurso() {
        return idTrilhaCurso;
    }
    
    public void setIdTrilhaCurso(Integer idTrilhaCurso) {
        this.idTrilhaCurso = idTrilhaCurso;
    }
    
    public Integer getIdTrilha() {
        return idTrilha;
    }
    
    public void setIdTrilha(Integer idTrilha) {
        this.idTrilha = idTrilha;
    }
    
    public Integer getIdCurso() {
        return idCurso;
    }
    
    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }
    
    public Integer getOrdemFase() {
        return ordemFase;
    }
    
    public void setOrdemFase(Integer ordemFase) {
        this.ordemFase = ordemFase;
    }
    
    public String getStatusFase() {
        return statusFase;
    }
    
    public void setStatusFase(String statusFase) {
        this.statusFase = statusFase;
    }
    
    public LocalDate getDataConclusao() {
        return dataConclusao;
    }
    
    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}
