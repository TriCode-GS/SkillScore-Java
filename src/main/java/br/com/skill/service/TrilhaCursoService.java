package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.CursoDAO;
import br.com.skill.dao.TrilhaCursoDAO;
import br.com.skill.dao.TrilhaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.TrilhaCurso;
import br.com.skill.model.TrilhaCursoCompleto;
import br.com.skill.model.Usuario;

public class TrilhaCursoService {
    
    TrilhaCursoDAO trilhaCursoDAO = new TrilhaCursoDAO();
    
    TrilhaDAO trilhaDAO = new TrilhaDAO();
    
    CursoDAO cursoDAO = new CursoDAO();
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public void salvar(TrilhaCurso trilhaCurso) {
        validarTrilhaCurso(trilhaCurso);
        
        if (trilhaCurso.getIdTrilha() == null) {
            Integer ultimoIdTrilha = trilhaDAO.obterUltimoIdTrilha();
            if (ultimoIdTrilha == null) {
                throw new IllegalArgumentException("Não há trilhas cadastradas no sistema. Cadastre uma trilha primeiro.");
            }
            trilhaCurso.setIdTrilha(ultimoIdTrilha);
        }
        
        if (trilhaDAO.buscarPorId(trilhaCurso.getIdTrilha()) == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        if (cursoDAO.buscarPorId(trilhaCurso.getIdCurso()) == null) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        
        trilhaCursoDAO.adicionar(trilhaCurso);
    }
    
    public void atualizar(TrilhaCurso trilhaCurso) {
        validarTrilhaCurso(trilhaCurso);
        
        TrilhaCurso trilhaCursoExistente = trilhaCursoDAO.buscarPorId(trilhaCurso.getIdTrilhaCurso());
        if (trilhaCursoExistente == null) {
            throw new IllegalArgumentException("TrilhaCurso não encontrado");
        }
        
        if (trilhaDAO.buscarPorId(trilhaCurso.getIdTrilha()) == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        if (cursoDAO.buscarPorId(trilhaCurso.getIdCurso()) == null) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        
        trilhaCursoDAO.atualizar(trilhaCurso);
    }
    
    public void deletar(Integer idTrilhaCurso) {
        TrilhaCurso trilhaCurso = trilhaCursoDAO.buscarPorId(idTrilhaCurso);
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não encontrado");
        }
        
        trilhaCursoDAO.removerPorId(idTrilhaCurso);
    }
    
    public TrilhaCurso buscarPorId(Integer idTrilhaCurso) {
        TrilhaCurso trilhaCurso = trilhaCursoDAO.buscarPorId(idTrilhaCurso);
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não encontrado");
        }
        return trilhaCurso;
    }
    
    public List<TrilhaCurso> listarTodos() {
        return trilhaCursoDAO.obterTodosTrilhaCursos();
    }
    
    public List<TrilhaCurso> buscarPorTrilha(Integer idTrilha) {
        return trilhaCursoDAO.buscarPorTrilha(idTrilha);
    }
    
    public List<TrilhaCursoCompleto> buscarPorTrilhaComDadosCurso(Integer idTrilha) {
        if (idTrilha == null) {
            throw new IllegalArgumentException("ID da Trilha é obrigatório");
        }
        
        if (trilhaDAO.buscarPorId(idTrilha) == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        return trilhaCursoDAO.buscarPorTrilhaComDadosCurso(idTrilha);
    }
    
    public List<TrilhaCurso> buscarPorCurso(Integer idCurso) {
        return trilhaCursoDAO.buscarPorCurso(idCurso);
    }
    
    public List<TrilhaCurso> buscarPorStatusFase(String statusFase) {
        return trilhaCursoDAO.buscarPorStatusFase(statusFase);
    }
    
    public void atualizarStatusFase(Integer idTrilhaCurso, String statusFase, Integer idUsuario) {
        if (idTrilhaCurso == null) {
            throw new IllegalArgumentException("ID do TrilhaCurso é obrigatório");
        }
        
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID do Usuário é obrigatório");
        }
        
        TrilhaCurso trilhaCurso = trilhaCursoDAO.buscarPorId(idTrilhaCurso);
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não encontrado");
        }
        
        // Valida se o TrilhaCurso pertence à trilha do usuário
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        if (usuario.getIdTrilha() == null) {
            throw new IllegalArgumentException("Usuário não está vinculado a nenhuma trilha");
        }
        
        if (!usuario.getIdTrilha().equals(trilhaCurso.getIdTrilha())) {
            throw new IllegalArgumentException("Este TrilhaCurso não pertence à trilha do usuário. O usuário está vinculado à trilha ID " + usuario.getIdTrilha() + ", mas o TrilhaCurso pertence à trilha ID " + trilhaCurso.getIdTrilha());
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
        
        boolean atualizado = trilhaCursoDAO.atualizarStatusFase(idTrilhaCurso, statusFaseUpper);
        if (!atualizado) {
            throw new RuntimeException("Erro ao atualizar status da fase");
        }
    }
    
    private void validarTrilhaCurso(TrilhaCurso trilhaCurso) {
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não pode ser nulo");
        }
        
        
        if (trilhaCurso.getIdCurso() == null) {
            throw new IllegalArgumentException("ID do Curso é obrigatório");
        }
        
        if (trilhaCurso.getOrdemFase() == null || trilhaCurso.getOrdemFase() < 1) {
            throw new IllegalArgumentException("Ordem da fase deve ser maior que zero");
        }
        
        if (trilhaCurso.getStatusFase() != null && !trilhaCurso.getStatusFase().trim().isEmpty()) {
            String statusFaseUpper = trilhaCurso.getStatusFase().toUpperCase().trim();
            if (!statusFaseUpper.equals("EM ANDAMENTO") && 
                !statusFaseUpper.equals("CONCLUIDA") && 
                !statusFaseUpper.equals("NAO INICIADA")) {
                throw new IllegalArgumentException("Status da fase inválido. Valores permitidos: EM ANDAMENTO, CONCLUIDA, NAO INICIADA");
            }
            trilhaCurso.setStatusFase(statusFaseUpper);
        }
    }
}
