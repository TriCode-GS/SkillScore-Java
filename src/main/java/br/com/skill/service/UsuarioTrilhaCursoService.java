package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.UsuarioTrilhaCursoDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.dao.TrilhaCursoDAO;
import br.com.skill.model.UsuarioTrilhaCurso;
import br.com.skill.model.Usuario;
import br.com.skill.model.TrilhaCurso;

public class UsuarioTrilhaCursoService {
    
    UsuarioTrilhaCursoDAO usuarioTrilhaCursoDAO = new UsuarioTrilhaCursoDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    TrilhaCursoDAO trilhaCursoDAO = new TrilhaCursoDAO();
    
    public void salvar(UsuarioTrilhaCurso usuarioTrilhaCurso) {
        validarUsuarioTrilhaCurso(usuarioTrilhaCurso);
        
        // Verifica se já existe registro para este usuário e trilha-curso
        UsuarioTrilhaCurso existente = usuarioTrilhaCursoDAO.buscarPorUsuarioETrilhaCurso(
            usuarioTrilhaCurso.getIdUsuario(), 
            usuarioTrilhaCurso.getIdTrilhaCurso()
        );
        
        if (existente != null) {
            throw new IllegalArgumentException("Já existe um registro para este usuário e trilha-curso. Use atualizar ao invés de salvar.");
        }
        
        // Valida se o usuário existe
        Usuario usuario = usuarioDAO.buscarPorId(usuarioTrilhaCurso.getIdUsuario());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        // Valida se o trilha-curso existe
        TrilhaCurso trilhaCurso = trilhaCursoDAO.buscarPorId(usuarioTrilhaCurso.getIdTrilhaCurso());
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não encontrado");
        }
        
        // Valida se o trilha-curso pertence à trilha do usuário
        if (usuario.getIdTrilha() == null) {
            throw new IllegalArgumentException("Usuário não está vinculado a nenhuma trilha");
        }
        
        if (!usuario.getIdTrilha().equals(trilhaCurso.getIdTrilha())) {
            throw new IllegalArgumentException("Este TrilhaCurso não pertence à trilha do usuário");
        }
        
        usuarioTrilhaCursoDAO.adicionar(usuarioTrilhaCurso);
    }
    
    public void atualizar(UsuarioTrilhaCurso usuarioTrilhaCurso) {
        validarUsuarioTrilhaCurso(usuarioTrilhaCurso);
        
        UsuarioTrilhaCurso existente = usuarioTrilhaCursoDAO.buscarPorId(usuarioTrilhaCurso.getIdUsuarioTrilhaCurso());
        if (existente == null) {
            throw new IllegalArgumentException("UsuarioTrilhaCurso não encontrado");
        }
        
        // Valida se o usuário existe
        Usuario usuario = usuarioDAO.buscarPorId(usuarioTrilhaCurso.getIdUsuario());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        // Valida se o trilha-curso existe
        TrilhaCurso trilhaCurso = trilhaCursoDAO.buscarPorId(usuarioTrilhaCurso.getIdTrilhaCurso());
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não encontrado");
        }
        
        usuarioTrilhaCursoDAO.atualizar(usuarioTrilhaCurso);
    }
    
    public void deletar(Integer idUsuarioTrilhaCurso) {
        UsuarioTrilhaCurso usuarioTrilhaCurso = usuarioTrilhaCursoDAO.buscarPorId(idUsuarioTrilhaCurso);
        if (usuarioTrilhaCurso == null) {
            throw new IllegalArgumentException("UsuarioTrilhaCurso não encontrado");
        }
        
        usuarioTrilhaCursoDAO.removerPorId(idUsuarioTrilhaCurso);
    }
    
    public UsuarioTrilhaCurso buscarPorId(Integer idUsuarioTrilhaCurso) {
        UsuarioTrilhaCurso usuarioTrilhaCurso = usuarioTrilhaCursoDAO.buscarPorId(idUsuarioTrilhaCurso);
        if (usuarioTrilhaCurso == null) {
            throw new IllegalArgumentException("UsuarioTrilhaCurso não encontrado");
        }
        return usuarioTrilhaCurso;
    }
    
    public List<UsuarioTrilhaCurso> listarTodos() {
        return usuarioTrilhaCursoDAO.obterTodos();
    }
    
    public List<UsuarioTrilhaCurso> buscarPorUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID do Usuário é obrigatório");
        }
        
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        return usuarioTrilhaCursoDAO.buscarPorUsuario(idUsuario);
    }
    
    public List<UsuarioTrilhaCurso> buscarPorTrilhaCurso(Integer idTrilhaCurso) {
        if (idTrilhaCurso == null) {
            throw new IllegalArgumentException("ID do TrilhaCurso é obrigatório");
        }
        
        TrilhaCurso trilhaCurso = trilhaCursoDAO.buscarPorId(idTrilhaCurso);
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não encontrado");
        }
        
        return usuarioTrilhaCursoDAO.buscarPorTrilhaCurso(idTrilhaCurso);
    }
    
    public List<UsuarioTrilhaCurso> buscarPorStatusFase(String statusFase) {
        if (statusFase == null || statusFase.trim().isEmpty()) {
            throw new IllegalArgumentException("Status da fase é obrigatório");
        }
        
        String statusFaseUpper = statusFase.toUpperCase().trim();
        if (!statusFaseUpper.equals("EM ANDAMENTO") && 
            !statusFaseUpper.equals("CONCLUIDA") && 
            !statusFaseUpper.equals("NAO INICIADA")) {
            throw new IllegalArgumentException("Status da fase inválido. Valores permitidos: EM ANDAMENTO, CONCLUIDA, NAO INICIADA");
        }
        
        return usuarioTrilhaCursoDAO.buscarPorStatusFase(statusFaseUpper);
    }
    
    public void atualizarStatusFase(Integer idUsuario, Integer idTrilhaCurso, String statusFase) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID do Usuário é obrigatório");
        }
        
        if (idTrilhaCurso == null) {
            throw new IllegalArgumentException("ID do TrilhaCurso é obrigatório");
        }
        
        // Valida se o usuário existe
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        // Valida se o trilha-curso existe
        TrilhaCurso trilhaCurso = trilhaCursoDAO.buscarPorId(idTrilhaCurso);
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não encontrado");
        }
        
        // Valida se o trilha-curso pertence à trilha do usuário
        if (usuario.getIdTrilha() == null) {
            throw new IllegalArgumentException("Usuário não está vinculado a nenhuma trilha");
        }
        
        if (!usuario.getIdTrilha().equals(trilhaCurso.getIdTrilha())) {
            throw new IllegalArgumentException("Este TrilhaCurso não pertence à trilha do usuário");
        }
        
        if (statusFase == null || statusFase.trim().isEmpty()) {
            throw new IllegalArgumentException("Status da fase é obrigatório");
        }
        
        String statusFaseUpper = statusFase.toUpperCase().trim();
        if (!statusFaseUpper.equals("EM ANDAMENTO") && 
            !statusFaseUpper.equals("CONCLUIDA") && 
            !statusFaseUpper.equals("NAO INICIADA")) {
            throw new IllegalArgumentException("Status da fase inválido. Valores permitidos: EM ANDAMENTO, CONCLUIDA, NAO INICIADA");
        }
        
        // Busca ou cria o registro
        UsuarioTrilhaCurso usuarioTrilhaCurso = usuarioTrilhaCursoDAO.buscarPorUsuarioETrilhaCurso(idUsuario, idTrilhaCurso);
        
        if (usuarioTrilhaCurso == null) {
            // Cria novo registro se não existir
            usuarioTrilhaCurso = new UsuarioTrilhaCurso();
            usuarioTrilhaCurso.setIdUsuario(idUsuario);
            usuarioTrilhaCurso.setIdTrilhaCurso(idTrilhaCurso);
            usuarioTrilhaCurso.setStatusFase(statusFaseUpper);
            usuarioTrilhaCursoDAO.adicionar(usuarioTrilhaCurso);
        } else {
            // Atualiza o registro existente
            boolean atualizado = usuarioTrilhaCursoDAO.atualizarStatusFasePorUsuarioETrilhaCurso(idUsuario, idTrilhaCurso, statusFaseUpper);
            if (!atualizado) {
                throw new RuntimeException("Erro ao atualizar status da fase");
            }
        }
    }
    
    private void validarUsuarioTrilhaCurso(UsuarioTrilhaCurso usuarioTrilhaCurso) {
        if (usuarioTrilhaCurso == null) {
            throw new IllegalArgumentException("UsuarioTrilhaCurso não pode ser nulo");
        }
        
        if (usuarioTrilhaCurso.getIdUsuario() == null) {
            throw new IllegalArgumentException("ID do Usuário é obrigatório");
        }
        
        if (usuarioTrilhaCurso.getIdTrilhaCurso() == null) {
            throw new IllegalArgumentException("ID do TrilhaCurso é obrigatório");
        }
        
        if (usuarioTrilhaCurso.getStatusFase() != null && !usuarioTrilhaCurso.getStatusFase().trim().isEmpty()) {
            String statusFaseUpper = usuarioTrilhaCurso.getStatusFase().toUpperCase().trim();
            if (!statusFaseUpper.equals("EM ANDAMENTO") && 
                !statusFaseUpper.equals("CONCLUIDA") && 
                !statusFaseUpper.equals("NAO INICIADA")) {
                throw new IllegalArgumentException("Status da fase inválido. Valores permitidos: EM ANDAMENTO, CONCLUIDA, NAO INICIADA");
            }
            usuarioTrilhaCurso.setStatusFase(statusFaseUpper);
        }
    }
}

