package br.com.skill.model;

public class Empresa {
    
    private Integer idEmpresa;
    private String nomeEmpresa;
    private String cnpj;
    private String setor;
    private Integer administrador;
    
    public Empresa() {
    }
    
    public Empresa(Integer idEmpresa, String nomeEmpresa, String cnpj, String setor, Integer administrador) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.setor = setor;
        this.administrador = administrador;
    }
    
    public Integer getIdEmpresa() {
        return idEmpresa;
    }
    
    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }
    
    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getSetor() {
        return setor;
    }
    
    public void setSetor(String setor) {
        this.setor = setor;
    }
    
    public Integer getAdministrador() {
        return administrador;
    }
    
    public void setAdministrador(Integer administrador) {
        this.administrador = administrador;
    }
}
