package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.EmpresaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.Empresa;
import br.com.skill.model.Usuario;

public class UsuarioService {
    
    UsuarioDAO usuarioDAO;
    
    EmpresaDAO empresaDAO;
    
    public void salvar(Usuario usuario) {
        validarUsuario(usuario);
        
        Usuario usuarioExistente = usuarioDAO.buscarPorEmail(usuario.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new IllegalArgumentException("Email já cadastrado no sistema");
        }
        
        if (usuario.getIdEmpresa() != null) {
            Empresa empresa = empresaDAO.buscarPorId(usuario.getIdEmpresa());
            if (empresa == null) {
                throw new IllegalArgumentException("Empresa não encontrada");
            }
        }
        
        usuarioDAO.adicionar(usuario);
    }
    
    public void atualizar(Usuario usuario) {
        validarUsuario(usuario);
        
        Usuario usuarioExistente = usuarioDAO.buscarPorId(usuario.getIdUsuario());
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        Usuario usuarioComEmail = usuarioDAO.buscarPorEmail(usuario.getEmail());
        if (usuarioComEmail != null && !usuarioComEmail.getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new IllegalArgumentException("Email já cadastrado para outro usuário");
        }
        
        if (usuario.getIdEmpresa() != null) {
            Empresa empresa = empresaDAO.buscarPorId(usuario.getIdEmpresa());
            if (empresa == null) {
                throw new IllegalArgumentException("Empresa não encontrada");
            }
        }
        
        usuarioDAO.atualizar(usuario);
    }
    
    public void deletar(Integer idUsuario) {
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        usuarioDAO.removerPorId(idUsuario);
    }
    
    public Usuario buscarPorId(Integer idUsuario) {
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return usuario;
    }
    
    public List<Usuario> listarTodos() {
        return usuarioDAO.obterTodosUsuarios();
    }
    
    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }
    
    public List<Usuario> buscarPorEmpresa(Integer idEmpresa) {
        return usuarioDAO.buscarPorEmpresa(idEmpresa);
    }
    
    public Usuario autenticar(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        Usuario usuario = usuarioDAO.buscarPorEmailESenha(email, senha);
        if (usuario == null) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }
        
        return usuario;
    }
    
    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        
        if (!usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        if (usuario.getSenha().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
        }
    }
}
