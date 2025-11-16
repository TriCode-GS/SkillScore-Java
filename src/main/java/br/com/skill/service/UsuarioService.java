package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.EmpresaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.Empresa;
import br.com.skill.model.Usuario;

public class UsuarioService {
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    EmpresaDAO empresaDAO = new EmpresaDAO();
    
    public void salvar(Usuario usuario) {
        validarUsuario(usuario);
        
        usuarioDAO.adicionar(usuario);
    }
    
    public void atualizar(Usuario usuario) {
        validarUsuario(usuario);
        
        Usuario usuarioExistente = usuarioDAO.buscarPorId(usuario.getIdUsuario());
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
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
    
    public List<Usuario> buscarPorEmpresa(Integer idEmpresa) {
        return usuarioDAO.buscarPorEmpresa(idEmpresa);
    }
    
    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        
        if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
    }
}
