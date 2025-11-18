package br.com.skill.service;

import java.time.LocalDate;
import java.util.List;

import br.com.skill.dao.LoginDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.Login;
import br.com.skill.model.Usuario;

public class LoginService {
    
    LoginDAO loginDAO = new LoginDAO();
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public void salvar(Login login) {
        validarLogin(login);
        
        
        Login loginExistente = loginDAO.buscarPorEmail(login.getEmail());
        if (loginExistente != null) {
            throw new IllegalArgumentException("Email já cadastrado para outro login");
        }
        
        if (login.getDataCriacao() == null) {
            login.setDataCriacao(LocalDate.now());
        }
        
        loginDAO.adicionar(login);
    }
    
    public void atualizar(Login login) {
        validarLogin(login);
        
        Login loginExistente = loginDAO.buscarPorId(login.getIdLogin());
        if (loginExistente == null) {
            throw new IllegalArgumentException("Login não encontrado");
        }
        
        Usuario usuario = usuarioDAO.buscarPorId(login.getIdUsuario());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        loginDAO.atualizar(login);
    }
    
    public void deletar(Integer idLogin) {
        Login login = loginDAO.buscarPorId(idLogin);
        if (login == null) {
            throw new IllegalArgumentException("Login não encontrado");
        }
        
        loginDAO.removerPorId(idLogin);
    }
    
    public Login buscarPorId(Integer idLogin) {
        Login login = loginDAO.buscarPorId(idLogin);
        if (login == null) {
            throw new IllegalArgumentException("Login não encontrado");
        }
        return login;
    }
    
    public List<Login> listarTodos() {
        return loginDAO.obterTodosLogins();
    }
    
    public List<Login> buscarPorUsuario(Integer idUsuario) {
        return loginDAO.buscarPorUsuario(idUsuario);
    }
    
    public Login autenticarAdministrador(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        Login login = loginDAO.buscarPorEmailESenha(email, senha);
        if (login == null) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }
        
        String tipoLogin = login.getTipoLogin();
        if (tipoLogin == null || !tipoLogin.toUpperCase().trim().equals("ADMINISTRADOR")) {
            throw new IllegalArgumentException("Acesso negado. Apenas administradores podem acessar esta área.");
        }
        
        return login;
    }
    
    public Login autenticarUsuario(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        Login login = loginDAO.buscarPorEmailESenha(email, senha);
        if (login == null) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }
        
        String tipoLogin = login.getTipoLogin();
        if (tipoLogin == null || !tipoLogin.toUpperCase().trim().equals("USUARIO")) {
            throw new IllegalArgumentException("Acesso negado. Este login é apenas para usuários comuns.");
        }
        
        return login;
    }
    
    private void validarLogin(Login login) {
        if (login == null) {
            throw new IllegalArgumentException("Login não pode ser nulo");
        }
        
        if (login.getEmail() == null || login.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        
        if (!login.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        
        if (login.getSenha() == null || login.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        if (login.getSenha().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
        }
        
        if (login.getTipoLogin() == null || login.getTipoLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de login é obrigatório");
        }
        
        String tipoLoginUpper = login.getTipoLogin().toUpperCase().trim();
        if (!tipoLoginUpper.equals("FUNCIONARIO") && 
            !tipoLoginUpper.equals("ADMINISTRADOR") && 
            !tipoLoginUpper.equals("USUARIO") && 
            !tipoLoginUpper.equals("GESTOR")) {
            throw new IllegalArgumentException("Tipo de login inválido. Valores permitidos: FUNCIONARIO, ADMINISTRADOR, USUARIO, GESTOR");
        }
        
        login.setTipoLogin(tipoLoginUpper);
    }
}
