package br.com.skill.model;

public class Usuario {
    
    private Integer idUsuario;
    private Integer idEmpresa;
    private Integer idDepartamento;
    private String nomeUsuario;
    private String tipoUsuario;
    private String nivelSenioridade;
    private String competencias;
    
    public Usuario() {
    }
    
    public Usuario(Integer idUsuario, Integer idEmpresa, Integer idDepartamento, String nome, String nomeUsuario,
                   String tipoUsuario, String nivelSenioridade, String competencias) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.idDepartamento = idDepartamento;
        this.nomeUsuario = nomeUsuario;
        this.tipoUsuario = tipoUsuario;
        this.nivelSenioridade = nivelSenioridade;
        this.competencias = competencias;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Integer getIdEmpresa() {
        return idEmpresa;
    }
    
    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    public Integer getIdDepartamento() {
        return idDepartamento;
    }
    
    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public String getNivelSenioridade() {
        return nivelSenioridade;
    }
    
    public void setNivelSenioridade(String nivelSenioridade) {
        this.nivelSenioridade = nivelSenioridade;
    }
    
    public String getCompetencias() {
        return competencias;
    }
    
    public void setCompetencias(String competencias) {
        this.competencias = competencias;
    }
}
