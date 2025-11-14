package br.com.skill.model;

public class Usuario {
    
    private Integer idUsuario;
    private Integer idEmpresa;
    private String nome;
    private String email;
    private String senha;
    private String tipoUsuario;
    private String areaAtuacao;
    private String nivelSenioridade;
    private String competencias;
    
    public Usuario() {
    }
    
    public Usuario(Integer idUsuario, Integer idEmpresa, String nome, String email, String senha, 
                   String tipoUsuario, String areaAtuacao, String nivelSenioridade, String competencias) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.areaAtuacao = areaAtuacao;
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
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public String getAreaAtuacao() {
        return areaAtuacao;
    }
    
    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
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
