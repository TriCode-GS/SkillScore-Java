package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.ProvaDAO;
import br.com.skill.dao.QuestaoDAO;
import br.com.skill.model.Questao;

public class QuestaoService {
    
    QuestaoDAO questaoDAO;
    
    ProvaDAO provaDAO;
    
    public void salvar(Questao questao) {
        validarQuestao(questao);
        
        if (provaDAO.buscarPorId(questao.getIdProva()) == null) {
            throw new IllegalArgumentException("Prova não encontrada");
        }
        
        questaoDAO.adicionar(questao);
    }
    
    public void atualizar(Questao questao) {
        validarQuestao(questao);
        
        Questao questaoExistente = questaoDAO.buscarPorId(questao.getIdQuestao());
        if (questaoExistente == null) {
            throw new IllegalArgumentException("Questão não encontrada");
        }
        
        if (provaDAO.buscarPorId(questao.getIdProva()) == null) {
            throw new IllegalArgumentException("Prova não encontrada");
        }
        
        questaoDAO.atualizar(questao);
    }
    
    public void deletar(Integer idQuestao) {
        Questao questao = questaoDAO.buscarPorId(idQuestao);
        if (questao == null) {
            throw new IllegalArgumentException("Questão não encontrada");
        }
        
        questaoDAO.removerPorId(idQuestao);
    }
    
    public Questao buscarPorId(Integer idQuestao) {
        Questao questao = questaoDAO.buscarPorId(idQuestao);
        if (questao == null) {
            throw new IllegalArgumentException("Questão não encontrada");
        }
        return questao;
    }
    
    public List<Questao> listarTodos() {
        return questaoDAO.obterTodasQuestoes();
    }
    
    public List<Questao> buscarPorProva(Integer idProva) {
        return questaoDAO.buscarPorProva(idProva);
    }
    
    private void validarQuestao(Questao questao) {
        if (questao == null) {
            throw new IllegalArgumentException("Questão não pode ser nula");
        }
        
        if (questao.getIdProva() == null) {
            throw new IllegalArgumentException("ID da Prova é obrigatório");
        }
        
        if (questao.getEnunciado() == null || questao.getEnunciado().trim().isEmpty()) {
            throw new IllegalArgumentException("Enunciado da questão é obrigatório");
        }
        
        if (questao.getEnunciado().length() > 500) {
            throw new IllegalArgumentException("Enunciado da questão deve ter no máximo 500 caracteres");
        }
        
        if (questao.getAlternativaA() == null || questao.getAlternativaA().trim().isEmpty()) {
            throw new IllegalArgumentException("Alternativa A é obrigatória");
        }
        
        if (questao.getAlternativaA().length() > 200) {
            throw new IllegalArgumentException("Alternativa A deve ter no máximo 200 caracteres");
        }
        
        if (questao.getAlternativaB() == null || questao.getAlternativaB().trim().isEmpty()) {
            throw new IllegalArgumentException("Alternativa B é obrigatória");
        }
        
        if (questao.getAlternativaB().length() > 200) {
            throw new IllegalArgumentException("Alternativa B deve ter no máximo 200 caracteres");
        }
        
        if (questao.getAlternativaC() == null || questao.getAlternativaC().trim().isEmpty()) {
            throw new IllegalArgumentException("Alternativa C é obrigatória");
        }
        
        if (questao.getAlternativaC().length() > 200) {
            throw new IllegalArgumentException("Alternativa C deve ter no máximo 200 caracteres");
        }
        
        if (questao.getAlternativaD() == null || questao.getAlternativaD().trim().isEmpty()) {
            throw new IllegalArgumentException("Alternativa D é obrigatória");
        }
        
        if (questao.getAlternativaD().length() > 200) {
            throw new IllegalArgumentException("Alternativa D deve ter no máximo 200 caracteres");
        }
        
        if (questao.getRespostaCorreta() == null || questao.getRespostaCorreta().trim().isEmpty()) {
            throw new IllegalArgumentException("Resposta correta é obrigatória");
        }
        
        if (!questao.getRespostaCorreta().matches("[A-Da-d]")) {
            throw new IllegalArgumentException("Resposta correta deve ser A, B, C ou D");
        }
    }
}

