package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.TrilhaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.Trilha;
import br.com.skill.model.Usuario;

public class TrilhaService {
    
    TrilhaDAO trilhaDAO = new TrilhaDAO();
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public void salvar(Trilha trilha) {
        validarTrilha(trilha);
        
        if (trilha.getIdUsuario() != null) {
            Usuario usuario = usuarioDAO.buscarPorId(trilha.getIdUsuario());
            if (usuario == null) {
                throw new IllegalArgumentException("Usuário não encontrado");
            }
        }
        
        trilhaDAO.adicionar(trilha);
    }
    
    public void atualizar(Trilha trilha) {
        validarTrilha(trilha);
        
        Trilha trilhaExistente = trilhaDAO.buscarPorId(trilha.getIdTrilha());
        if (trilhaExistente == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        if (trilha.getIdUsuario() != null) {
            Usuario usuario = usuarioDAO.buscarPorId(trilha.getIdUsuario());
            if (usuario == null) {
                throw new IllegalArgumentException("Usuário não encontrado");
            }
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
    
    public List<Trilha> buscarPorUsuario(Integer idUsuario) {
        return trilhaDAO.buscarPorUsuario(idUsuario);
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
        
        if (trilha.getIdUsuario() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
    }
}
