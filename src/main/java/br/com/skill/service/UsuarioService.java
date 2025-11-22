package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.DepartamentoDAO;
import br.com.skill.dao.EmpresaDAO;
import br.com.skill.dao.TrilhaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.Departamento;
import br.com.skill.model.Empresa;
import br.com.skill.model.Trilha;
import br.com.skill.model.Usuario;

public class UsuarioService {
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    EmpresaDAO empresaDAO = new EmpresaDAO();
    
    DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    
    TrilhaDAO trilhaDAO = new TrilhaDAO();
    
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
        
        if (usuario.getIdTrilha() != null) {
            Trilha trilha = trilhaDAO.buscarPorId(usuario.getIdTrilha());
            if (trilha == null) {
                throw new IllegalArgumentException("Trilha não encontrada");
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
        
        if (usuario.getIdTrilha() != null) {
            Trilha trilha = trilhaDAO.buscarPorId(usuario.getIdTrilha());
            if (trilha == null) {
                throw new IllegalArgumentException("Trilha não encontrada");
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
    
    public List<Usuario> buscarPorTrilha(Integer idTrilha) {
        return usuarioDAO.buscarPorTrilha(idTrilha);
    }
    
    public List<Usuario> buscarAdministradoresEmp() {
        return usuarioDAO.buscarPorTipoUsuario("ADMINISTRADOR EMP");
    }
    
    public List<Usuario> buscarGestores() {
        return usuarioDAO.buscarPorTipoUsuario("GESTOR");
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
    
    public void vincularGestorADepartamento(Integer idUsuario, Integer idDepartamento) {
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        String tipoUsuario = usuario.getTipoUsuario();
        if (tipoUsuario == null || !tipoUsuario.toUpperCase().trim().equals("GESTOR")) {
            throw new IllegalArgumentException("Apenas usuários do tipo GESTOR podem ser vinculados a departamentos");
        }
        
        Integer departamentoAtual = usuario.getIdDepartamento();
        if (departamentoAtual != null && idDepartamento != null) {
            if (!departamentoAtual.equals(idDepartamento)) {
                throw new IllegalStateException("Este gestor já está vinculado ao departamento ID " + departamentoAtual + 
                        ". Não é possível vincular a outro departamento. " +
                        "Para vincular a outro departamento, primeiro desvincule o gestor (envie idDepartamento: null).");
            }
        }
        
        if (idDepartamento != null) {
            Departamento departamento = departamentoDAO.buscarPorId(idDepartamento);
            if (departamento == null) {
                throw new IllegalArgumentException("Departamento não encontrado");
            }
        }
        
        boolean atualizado = usuarioDAO.atualizarIdDepartamento(idUsuario, idDepartamento);
        if (!atualizado) {
            throw new RuntimeException("Erro ao vincular gestor ao departamento");
        }
    }
    
    public void vincularTrilha(Integer idUsuario, Integer idTrilha) {
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        if (idTrilha != null) {
            Trilha trilha = trilhaDAO.buscarPorId(idTrilha);
            if (trilha == null) {
                throw new IllegalArgumentException("Trilha não encontrada");
            }
        }
        
        boolean atualizado = usuarioDAO.atualizarIdTrilha(idUsuario, idTrilha);
        if (!atualizado) {
            throw new RuntimeException("Erro ao vincular usuário à trilha");
        }
    }
    
    public void desvincularTrilha(Integer idUsuario) {
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        if (usuario.getIdTrilha() == null) {
            throw new IllegalStateException("Este usuário não está vinculado a nenhuma trilha");
        }
        
        boolean atualizado = usuarioDAO.atualizarIdTrilha(idUsuario, null);
        if (!atualizado) {
            throw new RuntimeException("Erro ao desvincular usuário da trilha");
        }
    }
    
    public void desvincularGestorDeDepartamento(Integer idUsuario) {
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        String tipoUsuario = usuario.getTipoUsuario();
        if (tipoUsuario == null || !tipoUsuario.toUpperCase().trim().equals("GESTOR")) {
            throw new IllegalArgumentException("Apenas usuários do tipo GESTOR podem ser desvinculados de departamentos");
        }
        
        if (usuario.getIdDepartamento() == null) {
            throw new IllegalStateException("Este gestor não está vinculado a nenhum departamento");
        }
        
        boolean atualizado = usuarioDAO.atualizarIdDepartamento(idUsuario, null);
        if (!atualizado) {
            throw new RuntimeException("Erro ao desvincular gestor do departamento");
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
