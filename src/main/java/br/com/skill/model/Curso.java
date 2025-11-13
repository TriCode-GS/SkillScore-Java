package br.com.skill.model;

public class Curso {
    
    private Integer idCurso;
    private String titulo;
    private String descricao;
    private String linkCurso;
    private String areaRelacionada;
    private String nivelRecomendado;
    private String duracaoHoras;
    
    public Curso() {
    }
    
    public Curso(Integer idCurso, String titulo, String descricao, String linkCurso, 
                 String areaRelacionada, String nivelRecomendado, String duracaoHoras) {
        this.idCurso = idCurso;
        this.titulo = titulo;
        this.descricao = descricao;
        this.linkCurso = linkCurso;
        this.areaRelacionada = areaRelacionada;
        this.nivelRecomendado = nivelRecomendado;
        this.duracaoHoras = duracaoHoras;
    }
    
    public Integer getIdCurso() {
        return idCurso;
    }
    
    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
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
