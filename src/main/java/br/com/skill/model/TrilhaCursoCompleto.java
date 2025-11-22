package br.com.skill.model;

import java.time.LocalDate;

public class TrilhaCursoCompleto {
    
    private Integer idTrilhaCurso;
    private Integer idTrilha;
    private Integer idCurso;
    private Integer ordemFase;
    private String statusFase;
    private LocalDate dataConclusao;
    
    private String titulo;
    private String descricao;
    private String linkCurso;
    private String areaRelacionada;
    private String nivelRecomendado;
    private String duracaoHoras;
    
    public TrilhaCursoCompleto() {
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
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getLinkCurso() {
        return linkCurso;
    }
    
    public void setLinkCurso(String linkCurso) {
        this.linkCurso = linkCurso;
    }
    
    public String getAreaRelacionada() {
        return areaRelacionada;
    }
    
    public void setAreaRelacionada(String areaRelacionada) {
        this.areaRelacionada = areaRelacionada;
    }
    
    public String getNivelRecomendado() {
        return nivelRecomendado;
    }
    
    public void setNivelRecomendado(String nivelRecomendado) {
        this.nivelRecomendado = nivelRecomendado;
    }
    
    public String getDuracaoHoras() {
        return duracaoHoras;
    }
    
    public void setDuracaoHoras(String duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }
}

