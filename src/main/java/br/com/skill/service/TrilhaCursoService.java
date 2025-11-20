package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.CursoDAO;
import br.com.skill.dao.TrilhaCursoDAO;
import br.com.skill.dao.TrilhaDAO;
import br.com.skill.model.TrilhaCurso;

public class TrilhaCursoService {
    
    TrilhaCursoDAO trilhaCursoDAO = new TrilhaCursoDAO();
    
    TrilhaDAO trilhaDAO = new TrilhaDAO();
    
    CursoDAO cursoDAO = new CursoDAO();
    
    public void salvar(TrilhaCurso trilhaCurso) {
        validarTrilhaCurso(trilhaCurso);
        
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
    
    public List<TrilhaCurso> buscarPorCurso(Integer idCurso) {
        return trilhaCursoDAO.buscarPorCurso(idCurso);
    }
    
    public List<TrilhaCurso> buscarPorStatusFase(String statusFase) {
        return trilhaCursoDAO.buscarPorStatusFase(statusFase);
    }
    
    private void validarTrilhaCurso(TrilhaCurso trilhaCurso) {
        if (trilhaCurso == null) {
            throw new IllegalArgumentException("TrilhaCurso não pode ser nulo");
        }
        
        if (trilhaCurso.getIdTrilha() == null) {
            throw new IllegalArgumentException("ID da Trilha é obrigatório");
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
