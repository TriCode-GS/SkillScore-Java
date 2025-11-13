package br.com.skill.service;

import java.time.LocalDate;
import java.util.List;

import br.com.skill.dao.LoginDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.Login;
import br.com.skill.model.Usuario;

public class LoginService {
    
    LoginDAO loginDAO;
    
    UsuarioDAO usuarioDAO;
    
    public void salvar(Login login) {
        validarLogin(login);
        
        Usuario usuario = usuarioDAO.buscarPorId(login.getUsuario().getIdUsuario());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        login.setUsuario(usuario);
        
        Login loginExistente = loginDAO.buscarPorEmail(login.getEmail());
        if (loginExistente != null && 
            !loginExistente.getIdLogin().equals(login.getIdLogin()) &&
            !loginExistente.getUsuario().getIdUsuario().equals(login.getUsuario().getIdUsuario())) {
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
        
        Usuario usuario = usuarioDAO.buscarPorId(login.getUsuario().getIdUsuario());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        login.setUsuario(usuario);
        
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
    
    public Login autenticar(String email, String senha) {
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
        
        if (login.getUsuario() == null || login.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
    }
}
