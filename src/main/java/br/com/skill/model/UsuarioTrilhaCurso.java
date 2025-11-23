package br.com.skill.model;

import java.time.LocalDate;

public class UsuarioTrilhaCurso {
    
    private Integer idUsuarioTrilhaCurso;
    private Integer idUsuario;
    private Integer idTrilhaCurso;
    private String statusFase;
    private LocalDate dataConclusao;
    
    public UsuarioTrilhaCurso() {
    }
    
    public UsuarioTrilhaCurso(Integer idUsuarioTrilhaCurso, Integer idUsuario, Integer idTrilhaCurso, 
                               String statusFase, LocalDate dataConclusao) {
        this.idUsuarioTrilhaCurso = idUsuarioTrilhaCurso;
        this.idUsuario = idUsuario;
        this.idTrilhaCurso = idTrilhaCurso;
        this.statusFase = statusFase;
        this.dataConclusao = dataConclusao;
    }
    
    public Integer getIdUsuarioTrilhaCurso() {
        return idUsuarioTrilhaCurso;
    }
    
    public void setIdUsuarioTrilhaCurso(Integer idUsuarioTrilhaCurso) {
        this.idUsuarioTrilhaCurso = idUsuarioTrilhaCurso;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Integer getIdTrilhaCurso() {
        return idTrilhaCurso;
    }
    
    public void setIdTrilhaCurso(Integer idTrilhaCurso) {
        this.idTrilhaCurso = idTrilhaCurso;
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


