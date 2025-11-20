package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.DepartamentoDAO;
import br.com.skill.dao.EmpresaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.Departamento;
import br.com.skill.model.Empresa;
import br.com.skill.model.Usuario;

public class UsuarioService {
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    EmpresaDAO empresaDAO = new EmpresaDAO();
    
    DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    
    public void salvar(Usuario usuario) {
        validarUsuario(usuario);
        
        if (usuario.getIdEmpresa() != null) {
            Empresa empresa = empresaDAO.buscarPorId(usuario.getIdEmpresa());
            if (empresa == null) {
                throw new IllegalArgumentException("Empresa não encontrada");
            }
        }
        
        if (usuario.getIdDepartamento() != null) {
            Departamento departamento = departamentoDAO.buscarPorId(usuario.getIdDepartamento());
            if (departamento == null) {
                throw new IllegalArgumentException("Departamento não encontrado");
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
        
        if (usuario.getIdEmpresa() != null) {
            Empresa empresa = empresaDAO.buscarPorId(usuario.getIdEmpresa());
            if (empresa == null) {
                throw new IllegalArgumentException("Empresa não encontrada");
            }
        }
        
        if (usuario.getIdDepartamento() != null) {
            Departamento departamento = departamentoDAO.buscarPorId(usuario.getIdDepartamento());
            if (departamento == null) {
                throw new IllegalArgumentException("Departamento não encontrado");
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
    
    public List<Usuario> buscarPorDepartamento(Integer idDepartamento) {
        return usuarioDAO.buscarPorDepartamento(idDepartamento);
    }
    
    public List<Usuario> buscarAdministradoresEmp() {
        return usuarioDAO.buscarPorTipoUsuario("ADMINISTRADOR EMP");
    }
    
    public void vincularEmpresa(Integer idUsuario, Integer idEmpresa) {
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        if (idEmpresa != null) {
            Empresa empresa = empresaDAO.buscarPorId(idEmpresa);
            if (empresa == null) {
                throw new IllegalArgumentException("Empresa não encontrada");
            }
        }
        
        boolean atualizado = usuarioDAO.atualizarIdEmpresa(idUsuario, idEmpresa);
        if (!atualizado) {
            throw new RuntimeException("Erro ao vincular usuário à empresa");
        }
    }
    
    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        
        if (usuario.getNomeUsuario() == null || usuario.getNomeUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
        
        if (usuario.getTipoUsuario() != null && !usuario.getTipoUsuario().trim().isEmpty()) {
            String tipoUsuarioUpper = usuario.getTipoUsuario().toUpperCase().trim();
            if (!tipoUsuarioUpper.equals("FUNCIONARIO") && 
                !tipoUsuarioUpper.equals("ADMINISTRADOR") && 
                !tipoUsuarioUpper.equals("USUARIO") && 
                !tipoUsuarioUpper.equals("GESTOR") && 
                !tipoUsuarioUpper.equals("ADMINISTRADOR EMP")) {
                throw new IllegalArgumentException("Tipo de usuário inválido. Valores permitidos: FUNCIONARIO, ADMINISTRADOR, USUARIO, GESTOR, ADMINISTRADOR EMP");
            }
            usuario.setTipoUsuario(tipoUsuarioUpper);
        }
    }
}
