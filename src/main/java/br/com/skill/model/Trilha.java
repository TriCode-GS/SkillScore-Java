package br.com.skill.model;

import java.time.LocalDate;

public class Trilha {
    
    private Integer idTrilha;
    private String nomeTrilha;
    private LocalDate dataCriacao;
    private String status;
    
    public Trilha() {
    }
    
    public Trilha(Integer idTrilha, String nomeTrilha, LocalDate dataCriacao, String status) {
        this.idTrilha = idTrilha;
        this.nomeTrilha = nomeTrilha;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }
    
    public Integer getIdTrilha() {
        return idTrilha;
    }
    
    public void setIdTrilha(Integer idTrilha) {
        this.idTrilha = idTrilha;
    }
    
    public String getNomeTrilha() {
        return nomeTrilha;
    }
    
    public void setNomeTrilha(String nomeTrilha) {
        this.nomeTrilha = nomeTrilha;
    }
    
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
