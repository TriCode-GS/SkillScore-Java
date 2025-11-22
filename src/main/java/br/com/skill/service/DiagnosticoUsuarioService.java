package br.com.skill.service;

import java.time.LocalDate;
import java.util.List;

import br.com.skill.dao.DiagnosticoUsuarioDAO;
import br.com.skill.dao.TrilhaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.DiagnosticoUsuario;
import br.com.skill.model.Trilha;

public class DiagnosticoUsuarioService {
    
    DiagnosticoUsuarioDAO diagnosticoUsuarioDAO = new DiagnosticoUsuarioDAO();
    
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    TrilhaDAO trilhaDAO = new TrilhaDAO();
    
    public void salvar(DiagnosticoUsuario diagnosticoUsuario) {
        validarDiagnosticoUsuario(diagnosticoUsuario);
        
        if (usuarioDAO.buscarPorId(diagnosticoUsuario.getIdUsuario()) == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        
        if (diagnosticoUsuario.getDataRealizacao() == null) {
            diagnosticoUsuario.setDataRealizacao(LocalDate.now());
        }
        
        Integer idTrilhaRecomendada = determinarTrilhaRecomendada(diagnosticoUsuario);
        
        if (idTrilhaRecomendada == null) {
            throw new IllegalArgumentException("Não foi possível determinar uma trilha recomendada. Verifique se existem trilhas cadastradas para ADMIN, TECH ou RH.");
        }
        
        diagnosticoUsuario.setIdTrilha(idTrilhaRecomendada);
        
        diagnosticoUsuarioDAO.adicionar(diagnosticoUsuario);
        
        usuarioDAO.atualizarIdTrilha(diagnosticoUsuario.getIdUsuario(), idTrilhaRecomendada);
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
        
        if (diagnosticoUsuario.getIdTrilha() == null) {
            Integer idTrilhaRecomendada = determinarTrilhaRecomendada(diagnosticoUsuario);
            if (idTrilhaRecomendada == null) {
                throw new IllegalArgumentException("Não foi possível determinar uma trilha recomendada. Verifique se existem trilhas cadastradas para ADMIN, TECH ou RH.");
            }
            diagnosticoUsuario.setIdTrilha(idTrilhaRecomendada);
        } else {
            if (trilhaDAO.buscarPorId(diagnosticoUsuario.getIdTrilha()) == null) {
                throw new IllegalArgumentException("Trilha não encontrada");
            }
        }
        
        diagnosticoUsuarioDAO.atualizar(diagnosticoUsuario);
        
        usuarioDAO.atualizarIdTrilha(diagnosticoUsuario.getIdUsuario(), diagnosticoUsuario.getIdTrilha());
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
    
    private Integer determinarTrilhaRecomendada(DiagnosticoUsuario diagnosticoUsuario) {
        Integer pontuacaoAdmin = diagnosticoUsuario.getPontuacaoAdmin() != null ? diagnosticoUsuario.getPontuacaoAdmin() : 0;
        Integer pontuacaoTech = diagnosticoUsuario.getPontuacaoTech() != null ? diagnosticoUsuario.getPontuacaoTech() : 0;
        Integer pontuacaoRh = diagnosticoUsuario.getPontuacaoRh() != null ? diagnosticoUsuario.getPontuacaoRh() : 0;
        
        String areaRecomendada;
        
        Integer maiorPontuacao = Math.max(Math.max(pontuacaoAdmin, pontuacaoTech), pontuacaoRh);
        
        if (maiorPontuacao == 0) {
            return null;
        }
        
        if (pontuacaoAdmin.equals(maiorPontuacao)) {
            areaRecomendada = "ADMIN";
        } else if (pontuacaoTech.equals(maiorPontuacao)) {
            areaRecomendada = "TECH";
        } else {
            areaRecomendada = "RH";
        }
        
        List<Trilha> trilhas = trilhaDAO.buscarPorNome(areaRecomendada);
        
        if (trilhas == null || trilhas.isEmpty()) {
            if ("ADMIN".equals(areaRecomendada)) {
                trilhas = trilhaDAO.buscarPorNome("ADMINISTRACAO");
                if (trilhas == null || trilhas.isEmpty()) {
                    trilhas = trilhaDAO.buscarPorNome("ADMINISTRATIVO");
                }
            } else if ("TECH".equals(areaRecomendada)) {
                trilhas = trilhaDAO.buscarPorNome("TECNOLOGIA");
                if (trilhas == null || trilhas.isEmpty()) {
                    trilhas = trilhaDAO.buscarPorNome("TECNICO");
                }
            } else if ("RH".equals(areaRecomendada)) {
                trilhas = trilhaDAO.buscarPorNome("RECURSOS HUMANOS");
                if (trilhas == null || trilhas.isEmpty()) {
                    trilhas = trilhaDAO.buscarPorNome("HUMANOS");
                }
            }
        }
        
        if (trilhas != null && !trilhas.isEmpty()) {
            return trilhas.get(0).getIdTrilha();
        }
        
        return null;
    }
    
    private void validarDiagnosticoUsuario(DiagnosticoUsuario diagnosticoUsuario) {
        if (diagnosticoUsuario == null) {
            throw new IllegalArgumentException("Diagnóstico usuário não pode ser nulo");
        }
        
        if (diagnosticoUsuario.getIdUsuario() == null) {
            throw new IllegalArgumentException("ID do Usuário é obrigatório");
        }
        
        // ID_TRILHA não é mais obrigatório, será calculado automaticamente
        // Mas se for informado, deve ser válido
        if (diagnosticoUsuario.getIdTrilha() != null) {
            if (trilhaDAO.buscarPorId(diagnosticoUsuario.getIdTrilha()) == null) {
                throw new IllegalArgumentException("Trilha não encontrada");
            }
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
        
        // Valida se pelo menos uma pontuação foi fornecida
        if ((diagnosticoUsuario.getPontuacaoAdmin() == null || diagnosticoUsuario.getPontuacaoAdmin() == 0) &&
            (diagnosticoUsuario.getPontuacaoTech() == null || diagnosticoUsuario.getPontuacaoTech() == 0) &&
            (diagnosticoUsuario.getPontuacaoRh() == null || diagnosticoUsuario.getPontuacaoRh() == 0)) {
            throw new IllegalArgumentException("Pelo menos uma pontuação (Admin, Tech ou RH) deve ser fornecida");
        }
    }
}


