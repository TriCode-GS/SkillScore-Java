package br.com.skill.service;

import java.time.LocalDate;
import java.util.List;

import br.com.skill.dao.ProvaDAO;
import br.com.skill.dao.ProvaUsuarioDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.ProvaUsuario;

public class ProvaUsuarioService {
    
    ProvaUsuarioDAO provaUsuarioDAO = new ProvaUsuarioDAO();
    
    ProvaDAO provaDAO = new ProvaDAO();
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public void salvar(ProvaUsuario provaUsuario) {
        validarProvaUsuario(provaUsuario);
        
        if (provaDAO.buscarPorId(provaUsuario.getIdProva()) == null) {
            throw new IllegalArgumentException("Prova não encontrada");
        }
        
        if (usuarioDAO.buscarPorId(provaUsuario.getIdUsuario()) == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        if (provaUsuario.getDataRealizacao() == null) {
            provaUsuario.setDataRealizacao(LocalDate.now());
        }
        
        provaUsuarioDAO.adicionar(provaUsuario);
    }
    
    public void atualizar(ProvaUsuario provaUsuario) {
        validarProvaUsuario(provaUsuario);
        
        ProvaUsuario provaUsuarioExistente = provaUsuarioDAO.buscarPorId(provaUsuario.getIdProvaUsuario());
        if (provaUsuarioExistente == null) {
            throw new IllegalArgumentException("Prova usuário não encontrada");
        }
        
        if (provaDAO.buscarPorId(provaUsuario.getIdProva()) == null) {
            throw new IllegalArgumentException("Prova não encontrada");
        }
        
        if (usuarioDAO.buscarPorId(provaUsuario.getIdUsuario()) == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        provaUsuarioDAO.atualizar(provaUsuario);
    }
    
    public void deletar(Integer idProvaUsuario) {
        ProvaUsuario provaUsuario = provaUsuarioDAO.buscarPorId(idProvaUsuario);
        if (provaUsuario == null) {
            throw new IllegalArgumentException("Prova usuário não encontrada");
        }
        
        provaUsuarioDAO.removerPorId(idProvaUsuario);
    }
    
    public ProvaUsuario buscarPorId(Integer idProvaUsuario) {
        ProvaUsuario provaUsuario = provaUsuarioDAO.buscarPorId(idProvaUsuario);
        if (provaUsuario == null) {
            throw new IllegalArgumentException("Prova usuário não encontrada");
        }
        return provaUsuario;
    }
    
    public List<ProvaUsuario> listarTodos() {
        return provaUsuarioDAO.obterTodasProvasUsuario();
    }
    
    public List<ProvaUsuario> buscarPorProva(Integer idProva) {
        return provaUsuarioDAO.buscarPorProva(idProva);
    }
    
    public List<ProvaUsuario> buscarPorUsuario(Integer idUsuario) {
        return provaUsuarioDAO.buscarPorUsuario(idUsuario);
    }
    
    private void validarProvaUsuario(ProvaUsuario provaUsuario) {
        if (provaUsuario == null) {
            throw new IllegalArgumentException("Prova usuário não pode ser nula");
        }
        
        if (provaUsuario.getIdProva() == null) {
            throw new IllegalArgumentException("ID da Prova é obrigatório");
        }
        
        if (provaUsuario.getIdUsuario() == null) {
            throw new IllegalArgumentException("ID do Usuário é obrigatório");
        }
        
        if (provaUsuario.getNotaObtida() != null && (provaUsuario.getNotaObtida() < 0 || provaUsuario.getNotaObtida() > 10)) {
            throw new IllegalArgumentException("Nota obtida deve estar entre 0 e 10");
        }
        
        if (provaUsuario.getPercentualAcerto() != null && 
            (provaUsuario.getPercentualAcerto() < 0 || provaUsuario.getPercentualAcerto() > 100)) {
            throw new IllegalArgumentException("Percentual de acerto deve estar entre 0 e 100");
        }
        
        if (provaUsuario.getAprovado() != null && !provaUsuario.getAprovado().trim().isEmpty()) {
            String aprovadoUpper = provaUsuario.getAprovado().toUpperCase().trim();
            if (!aprovadoUpper.equals("S") && !aprovadoUpper.equals("N")) {
                throw new IllegalArgumentException("Aprovado deve ser 'S' ou 'N'");
            }
            provaUsuario.setAprovado(aprovadoUpper);
        }
    }
}

