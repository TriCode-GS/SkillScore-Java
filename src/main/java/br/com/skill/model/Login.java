package br.com.skill.model;

import java.time.LocalDate;

public class Login {
    
    private Integer idLogin;
    private Integer idUsuario;
    private String email;
    private String senha;
    private String tipoLogin;
    private LocalDate dataCriacao;
    
    public Login() {
    }
    
    public Login(Integer idLogin, Integer idUsuario, String email, String senha, String tipoLogin, LocalDate dataCriacao) {
        this.idLogin = idLogin;
        this.idUsuario = idUsuario;
        this.email = email;
        this.senha = senha;
        this.tipoLogin = tipoLogin;
        this.dataCriacao = dataCriacao;
    }
    
    public Integer getIdLogin() {
        return idLogin;
    }
    
    public void setIdLogin(Integer idLogin) {
        this.idLogin = idLogin;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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
    
    public String getTipoLogin() {
        return tipoLogin;
    }
    
    public void setTipoLogin(String tipoLogin) {
        this.tipoLogin = tipoLogin;
    }
    
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
