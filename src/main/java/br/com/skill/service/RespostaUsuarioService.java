package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.ProvaUsuarioDAO;
import br.com.skill.dao.QuestaoDAO;
import br.com.skill.dao.RespostaUsuarioDAO;
import br.com.skill.model.RespostaUsuario;

public class RespostaUsuarioService {
    
    RespostaUsuarioDAO respostaUsuarioDAO = new RespostaUsuarioDAO();
    
    ProvaUsuarioDAO provaUsuarioDAO = new ProvaUsuarioDAO();
    
    QuestaoDAO questaoDAO = new QuestaoDAO();
    
    public void salvar(RespostaUsuario respostaUsuario) {
        validarRespostaUsuario(respostaUsuario);
        
        if (provaUsuarioDAO.buscarPorId(respostaUsuario.getIdProvaUsuario()) == null) {
            throw new IllegalArgumentException("Prova usuário não encontrada");
        }
        
        if (questaoDAO.buscarPorId(respostaUsuario.getIdQuestao()) == null) {
            throw new IllegalArgumentException("Questão não encontrada");
        }
        
        respostaUsuarioDAO.adicionar(respostaUsuario);
    }
    
    public void atualizar(RespostaUsuario respostaUsuario) {
        validarRespostaUsuario(respostaUsuario);
        
        RespostaUsuario respostaUsuarioExistente = respostaUsuarioDAO.buscarPorId(respostaUsuario.getIdRespostaUsuario());
        if (respostaUsuarioExistente == null) {
            throw new IllegalArgumentException("Resposta usuário não encontrada");
        }
        
        if (provaUsuarioDAO.buscarPorId(respostaUsuario.getIdProvaUsuario()) == null) {
            throw new IllegalArgumentException("Prova usuário não encontrada");
        }
        
        if (questaoDAO.buscarPorId(respostaUsuario.getIdQuestao()) == null) {
            throw new IllegalArgumentException("Questão não encontrada");
        }
        
        respostaUsuarioDAO.atualizar(respostaUsuario);
    }
    
    public void deletar(Integer idRespostaUsuario) {
        RespostaUsuario respostaUsuario = respostaUsuarioDAO.buscarPorId(idRespostaUsuario);
        if (respostaUsuario == null) {
            throw new IllegalArgumentException("Resposta usuário não encontrada");
        }
        
        respostaUsuarioDAO.removerPorId(idRespostaUsuario);
    }
    
    public RespostaUsuario buscarPorId(Integer idRespostaUsuario) {
        RespostaUsuario respostaUsuario = respostaUsuarioDAO.buscarPorId(idRespostaUsuario);
        if (respostaUsuario == null) {
            throw new IllegalArgumentException("Resposta usuário não encontrada");
        }
        return respostaUsuario;
    }
    
    public List<RespostaUsuario> listarTodos() {
        return respostaUsuarioDAO.obterTodasRespostasUsuario();
    }
    
    public List<RespostaUsuario> buscarPorProvaUsuario(Integer idProvaUsuario) {
        return respostaUsuarioDAO.buscarPorProvaUsuario(idProvaUsuario);
    }
    
    public List<RespostaUsuario> buscarPorQuestao(Integer idQuestao) {
        return respostaUsuarioDAO.buscarPorQuestao(idQuestao);
    }
    
    private void validarRespostaUsuario(RespostaUsuario respostaUsuario) {
        if (respostaUsuario == null) {
            throw new IllegalArgumentException("Resposta usuário não pode ser nula");
        }
        
        if (respostaUsuario.getIdProvaUsuario() == null) {
            throw new IllegalArgumentException("ID da Prova Usuário é obrigatório");
        }
        
        if (respostaUsuario.getIdQuestao() == null) {
            throw new IllegalArgumentException("ID da Questão é obrigatório");
        }
        
        if (respostaUsuario.getResposta() == null || respostaUsuario.getResposta().trim().isEmpty()) {
            throw new IllegalArgumentException("Resposta é obrigatória");
        }
        
        String respostaUpper = respostaUsuario.getResposta().toUpperCase().trim();
        if (!respostaUpper.equals("A") && !respostaUpper.equals("B") && 
            !respostaUpper.equals("C") && !respostaUpper.equals("D")) {
            throw new IllegalArgumentException("Resposta deve ser A, B, C ou D");
        }
        respostaUsuario.setResposta(respostaUpper);
        
        if (respostaUsuario.getCorreta() != null && !respostaUsuario.getCorreta().trim().isEmpty()) {
            String corretaUpper = respostaUsuario.getCorreta().toUpperCase().trim();
            if (!corretaUpper.equals("S") && !corretaUpper.equals("N")) {
                throw new IllegalArgumentException("Correta deve ser 'S' ou 'N'");
            }
            respostaUsuario.setCorreta(corretaUpper);
        }
    }
}

