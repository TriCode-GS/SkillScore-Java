package br.com.skill.service;

import java.time.LocalDate;
import java.util.List;

import br.com.skill.dao.ProvaDAO;
import br.com.skill.dao.TrilhaDAO;
import br.com.skill.model.Prova;

public class ProvaService {
    
    ProvaDAO provaDAO;
    
    TrilhaDAO trilhaDAO;
    
    public void salvar(Prova prova) {
        validarProva(prova);
        
        if (trilhaDAO.buscarPorId(prova.getIdTrilha()) == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        if (prova.getDataCriacao() == null) {
            prova.setDataCriacao(LocalDate.now());
        }
        
        provaDAO.adicionar(prova);
    }
    
    public void atualizar(Prova prova) {
        validarProva(prova);
        
        Prova provaExistente = provaDAO.buscarPorId(prova.getIdProva());
        if (provaExistente == null) {
            throw new IllegalArgumentException("Prova não encontrada");
        }
        
        if (trilhaDAO.buscarPorId(prova.getIdTrilha()) == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        provaDAO.atualizar(prova);
    }
    
    public void deletar(Integer idProva) {
        Prova prova = provaDAO.buscarPorId(idProva);
        if (prova == null) {
            throw new IllegalArgumentException("Prova não encontrada");
        }
        
        provaDAO.removerPorId(idProva);
    }
    
    public Prova buscarPorId(Integer idProva) {
        Prova prova = provaDAO.buscarPorId(idProva);
        if (prova == null) {
            throw new IllegalArgumentException("Prova não encontrada");
        }
        return prova;
    }
    
    public List<Prova> listarTodos() {
        return provaDAO.obterTodasProvas();
    }
    
    public List<Prova> buscarPorTrilha(Integer idTrilha) {
        return provaDAO.buscarPorTrilha(idTrilha);
    }
    
    private void validarProva(Prova prova) {
        if (prova == null) {
            throw new IllegalArgumentException("Prova não pode ser nula");
        }
        
        if (prova.getIdTrilha() == null) {
            throw new IllegalArgumentException("ID da Trilha é obrigatório");
        }
        
        if (prova.getTitulo() == null || prova.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título da prova é obrigatório");
        }
        
        if (prova.getTitulo().length() > 100) {
            throw new IllegalArgumentException("Título da prova deve ter no máximo 100 caracteres");
        }
        
        if (prova.getDescricao() != null && prova.getDescricao().length() > 300) {
            throw new IllegalArgumentException("Descrição da prova deve ter no máximo 300 caracteres");
        }
        
        if (prova.getQtdeQuestoes() != null && prova.getQtdeQuestoes() < 0) {
            throw new IllegalArgumentException("Quantidade de questões não pode ser negativa");
        }
    }
}

