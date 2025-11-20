package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.TrilhaDAO;
import br.com.skill.model.Trilha;

public class TrilhaService {
    
    TrilhaDAO trilhaDAO = new TrilhaDAO();
    
    public void salvar(Trilha trilha) {
        validarTrilha(trilha);
        trilhaDAO.adicionar(trilha);
    }
    
    public void atualizar(Trilha trilha) {
        validarTrilha(trilha);
        
        Trilha trilhaExistente = trilhaDAO.buscarPorId(trilha.getIdTrilha());
        if (trilhaExistente == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        trilhaDAO.atualizar(trilha);
    }
    
    public void deletar(Integer idTrilha) {
        Trilha trilha = trilhaDAO.buscarPorId(idTrilha);
        if (trilha == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        trilhaDAO.removerPorId(idTrilha);
    }
    
    public Trilha buscarPorId(Integer idTrilha) {
        Trilha trilha = trilhaDAO.buscarPorId(idTrilha);
        if (trilha == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        return trilha;
    }
    
    public List<Trilha> listarTodos() {
        return trilhaDAO.obterTodasTrilhas();
    }
    
    public List<Trilha> buscarPorStatus(String status) {
        return trilhaDAO.buscarPorStatus(status);
    }
    
    public List<Trilha> buscarPorNome(String nomeTrilha) {
        return trilhaDAO.buscarPorNome(nomeTrilha);
    }
    
    private void validarTrilha(Trilha trilha) {
        if (trilha == null) {
            throw new IllegalArgumentException("Trilha não pode ser nula");
        }
        
        if (trilha.getNomeTrilha() == null || trilha.getNomeTrilha().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da trilha é obrigatório");
        }
        
        if (trilha.getNomeTrilha().length() > 50) {
            throw new IllegalArgumentException("Nome da trilha deve ter no máximo 50 caracteres");
        }
        
        if (trilha.getStatus() != null && !trilha.getStatus().trim().isEmpty()) {
            String statusUpper = trilha.getStatus().toUpperCase().trim();
            if (!statusUpper.equals("EM ANDAMENTO") && 
                !statusUpper.equals("CONCLUIDA") && 
                !statusUpper.equals("NAO INICIADA")) {
                throw new IllegalArgumentException("Status inválido. Valores permitidos: EM ANDAMENTO, CONCLUIDA, NAO INICIADA");
            }
            trilha.setStatus(statusUpper);
        }
    }
}
