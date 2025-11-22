package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.TrilhaDAO;
import br.com.skill.model.Trilha;

public class TrilhaService {
    
    TrilhaDAO trilhaDAO = new TrilhaDAO();
    
    public void salvar(Trilha trilha) {
        validarTrilha(trilha);
        
        // Verifica se já existe uma trilha com o mesmo nome
        Trilha trilhaExistente = trilhaDAO.buscarPorNomeExato(trilha.getNomeTrilha());
        if (trilhaExistente != null) {
            throw new IllegalArgumentException("Já existe uma trilha com o nome '" + trilha.getNomeTrilha() + "'");
        }
        
        trilhaDAO.adicionar(trilha);
    }
    
    public void atualizar(Trilha trilha) {
        validarTrilha(trilha);
        
        Trilha trilhaExistente = trilhaDAO.buscarPorId(trilha.getIdTrilha());
        if (trilhaExistente == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        // Verifica se já existe outra trilha com o mesmo nome (exceto a própria trilha sendo atualizada)
        Trilha trilhaComMesmoNome = trilhaDAO.buscarPorNomeExato(trilha.getNomeTrilha());
        if (trilhaComMesmoNome != null && !trilhaComMesmoNome.getIdTrilha().equals(trilha.getIdTrilha())) {
            throw new IllegalArgumentException("Já existe outra trilha com o nome '" + trilha.getNomeTrilha() + "'");
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
        
        if (trilha.getNumFases() != null && trilha.getNumFases() < 1) {
            throw new IllegalArgumentException("Número de fases deve ser maior que zero");
        }
    }
}
