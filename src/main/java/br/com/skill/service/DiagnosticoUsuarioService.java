package br.com.skill.service;

import java.time.LocalDate;
import java.util.List;

import br.com.skill.dao.DiagnosticoUsuarioDAO;
import br.com.skill.dao.TrilhaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.DiagnosticoUsuario;

public class DiagnosticoUsuarioService {
    
    DiagnosticoUsuarioDAO diagnosticoUsuarioDAO = new DiagnosticoUsuarioDAO();
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    TrilhaDAO trilhaDAO = new TrilhaDAO();
    
    public void salvar(DiagnosticoUsuario diagnosticoUsuario) {
        validarDiagnosticoUsuario(diagnosticoUsuario);
        
        if (usuarioDAO.buscarPorId(diagnosticoUsuario.getIdUsuario()) == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        if (trilhaDAO.buscarPorId(diagnosticoUsuario.getIdTrilha()) == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        if (diagnosticoUsuario.getDataRealizacao() == null) {
            diagnosticoUsuario.setDataRealizacao(LocalDate.now());
        }
        
        diagnosticoUsuarioDAO.adicionar(diagnosticoUsuario);
    }
    
    public void atualizar(DiagnosticoUsuario diagnosticoUsuario) {
        validarDiagnosticoUsuario(diagnosticoUsuario);
        
        DiagnosticoUsuario diagnosticoExistente = diagnosticoUsuarioDAO.buscarPorId(diagnosticoUsuario.getIdDiagnostico());
        if (diagnosticoExistente == null) {
            throw new IllegalArgumentException("Diagnóstico usuário não encontrado");
        }
        
        if (usuarioDAO.buscarPorId(diagnosticoUsuario.getIdUsuario()) == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        if (trilhaDAO.buscarPorId(diagnosticoUsuario.getIdTrilha()) == null) {
            throw new IllegalArgumentException("Trilha não encontrada");
        }
        
        diagnosticoUsuarioDAO.atualizar(diagnosticoUsuario);
    }
    
    public void deletar(Integer idDiagnostico) {
        DiagnosticoUsuario diagnostico = diagnosticoUsuarioDAO.buscarPorId(idDiagnostico);
        if (diagnostico == null) {
            throw new IllegalArgumentException("Diagnóstico usuário não encontrado");
        }
        
        diagnosticoUsuarioDAO.removerPorId(idDiagnostico);
    }
    
    public DiagnosticoUsuario buscarPorId(Integer idDiagnostico) {
        DiagnosticoUsuario diagnostico = diagnosticoUsuarioDAO.buscarPorId(idDiagnostico);
        if (diagnostico == null) {
            throw new IllegalArgumentException("Diagnóstico usuário não encontrado");
        }
        return diagnostico;
    }
    
    public List<DiagnosticoUsuario> listarTodos() {
        return diagnosticoUsuarioDAO.obterTodosDiagnosticos();
    }
    
    public List<DiagnosticoUsuario> buscarPorUsuario(Integer idUsuario) {
        return diagnosticoUsuarioDAO.buscarPorUsuario(idUsuario);
    }
    
    public List<DiagnosticoUsuario> buscarPorTrilha(Integer idTrilha) {
        return diagnosticoUsuarioDAO.buscarPorTrilha(idTrilha);
    }
    
    public DiagnosticoUsuario buscarUltimoPorUsuario(Integer idUsuario) {
        DiagnosticoUsuario diagnostico = diagnosticoUsuarioDAO.buscarUltimoPorUsuario(idUsuario);
        if (diagnostico == null) {
            throw new IllegalArgumentException("Nenhum diagnóstico encontrado para o usuário");
        }
        return diagnostico;
    }
    
    private void validarDiagnosticoUsuario(DiagnosticoUsuario diagnosticoUsuario) {
        if (diagnosticoUsuario == null) {
            throw new IllegalArgumentException("Diagnóstico usuário não pode ser nulo");
        }
        
        if (diagnosticoUsuario.getIdUsuario() == null) {
            throw new IllegalArgumentException("ID do Usuário é obrigatório");
        }
        
        if (diagnosticoUsuario.getIdTrilha() == null) {
            throw new IllegalArgumentException("ID da Trilha é obrigatório");
        }
        
        if (diagnosticoUsuario.getPontuacaoAdmin() != null && diagnosticoUsuario.getPontuacaoAdmin() < 0) {
            throw new IllegalArgumentException("Pontuação Admin não pode ser negativa");
        }
        
        if (diagnosticoUsuario.getPontuacaoTech() != null && diagnosticoUsuario.getPontuacaoTech() < 0) {
            throw new IllegalArgumentException("Pontuação Tech não pode ser negativa");
        }
        
        if (diagnosticoUsuario.getPontuacaoRh() != null && diagnosticoUsuario.getPontuacaoRh() < 0) {
            throw new IllegalArgumentException("Pontuação RH não pode ser negativa");
        }
    }
}

