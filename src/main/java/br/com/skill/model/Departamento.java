package br.com.skill.model;

import java.time.LocalDate;

public class Departamento {
    
    private Integer idDepartamento;
    private Integer idEmpresa;
    private String nomeDepartamento;
    private LocalDate dataCriacao;
    
    public Departamento() {
    }
    
    public Departamento(Integer idDepartamento, Integer idEmpresa, String nomeDepartamento, LocalDate dataCriacao) {
        this.idDepartamento = idDepartamento;
        this.idEmpresa = idEmpresa;
        this.nomeDepartamento = nomeDepartamento;
        this.dataCriacao = dataCriacao;
    }
    
    public Integer getIdDepartamento() {
        return idDepartamento;
    }
    
    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    
    public Integer getIdEmpresa() {
        return idEmpresa;
    }
    
    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    public String getNomeDepartamento() {
        return nomeDepartamento;
    }
    
    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }
    
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

