package br.com.skill.model;

import java.time.LocalDate;

public class Prova {
    
    private Integer idProva;
    private Integer idTrilha;
    private String titulo;
    private String descricao;
    private Integer qtdeQuestoes;
    private LocalDate dataCriacao;
    
    public Prova() {
    }
    
    public Prova(Integer idProva, Integer idTrilha, String titulo, String descricao, 
                 Integer qtdeQuestoes, LocalDate dataCriacao) {
        this.idProva = idProva;
        this.idTrilha = idTrilha;
        this.titulo = titulo;
        this.descricao = descricao;
        this.qtdeQuestoes = qtdeQuestoes;
        this.dataCriacao = dataCriacao;
    }
    
    public Integer getIdProva() {
        return idProva;
    }
    
    public void setIdProva(Integer idProva) {
        this.idProva = idProva;
    }
    
    public Integer getIdTrilha() {
        return idTrilha;
    }
    
    public void setIdTrilha(Integer idTrilha) {
        this.idTrilha = idTrilha;
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
    
    public Integer getQtdeQuestoes() {
        return qtdeQuestoes;
    }
    
    public void setQtdeQuestoes(Integer qtdeQuestoes) {
        this.qtdeQuestoes = qtdeQuestoes;
    }
    
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

