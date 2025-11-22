package br.com.skill.dao;

import br.com.skill.model.DiagnosticoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class DiagnosticoUsuarioDAO {
    
    public void adicionar(DiagnosticoUsuario diagnosticoUsuario) {
        String sql = "INSERT INTO TB_SS_DIAGNOSTICO_USUARIO (ID_DIAGNOSTICO, ID_USUARIO, ID_TRILHA, "
                + "PONTUACAO_ADMIN, PONTUACAO_TECH, PONTUACAO_RH, DATA_REALIZACAO) "
                + "VALUES(SQ_SS_DIAGNOSTICO_USUARIO.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setInt(1, diagnosticoUsuario.getIdUsuario());
            comandoDeInsercao.setInt(2, diagnosticoUsuario.getIdTrilha());
            
            if (diagnosticoUsuario.getPontuacaoAdmin() != null) {
                comandoDeInsercao.setInt(3, diagnosticoUsuario.getPontuacaoAdmin());
            } else {
                comandoDeInsercao.setNull(3, Types.INTEGER);
            }
            
            if (diagnosticoUsuario.getPontuacaoTech() != null) {
                comandoDeInsercao.setInt(4, diagnosticoUsuario.getPontuacaoTech());
            } else {
                comandoDeInsercao.setNull(4, Types.INTEGER);
            }
            
            if (diagnosticoUsuario.getPontuacaoRh() != null) {
                comandoDeInsercao.setInt(5, diagnosticoUsuario.getPontuacaoRh());
            } else {
                comandoDeInsercao.setNull(5, Types.INTEGER);
            }
            
            if (diagnosticoUsuario.getDataRealizacao() != null) {
                comandoDeInsercao.setObject(6, diagnosticoUsuario.getDataRealizacao());
            } else {
                comandoDeInsercao.setNull(6, Types.DATE);
            }
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<DiagnosticoUsuario> obterTodosDiagnosticos() {
        ArrayList<DiagnosticoUsuario> diagnosticos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_DIAGNOSTICO_USUARIO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                DiagnosticoUsuario diagnostico = criarDiagnosticoDoResultSet(rs);
                diagnosticos.add(diagnostico);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return diagnosticos;
    }
    
    public boolean atualizar(DiagnosticoUsuario diagnosticoUsuario) {
        String sql = "UPDATE TB_SS_DIAGNOSTICO_USUARIO "
                + "SET ID_USUARIO = ?, ID_TRILHA = ?, PONTUACAO_ADMIN = ?, PONTUACAO_TECH = ?, "
                + "PONTUACAO_RH = ?, DATA_REALIZACAO = ? "
                + "WHERE ID_DIAGNOSTICO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(diagnosticoUsuario.getIdDiagnostico())) {
                return false;
            }
            
            comandoDeAtualizacao.setInt(1, diagnosticoUsuario.getIdUsuario());
            comandoDeAtualizacao.setInt(2, diagnosticoUsuario.getIdTrilha());
            
            if (diagnosticoUsuario.getPontuacaoAdmin() != null) {
                comandoDeAtualizacao.setInt(3, diagnosticoUsuario.getPontuacaoAdmin());
            } else {
                comandoDeAtualizacao.setNull(3, Types.INTEGER);
            }
            
            if (diagnosticoUsuario.getPontuacaoTech() != null) {
                comandoDeAtualizacao.setInt(4, diagnosticoUsuario.getPontuacaoTech());
            } else {
                comandoDeAtualizacao.setNull(4, Types.INTEGER);
            }
            
            if (diagnosticoUsuario.getPontuacaoRh() != null) {
                comandoDeAtualizacao.setInt(5, diagnosticoUsuario.getPontuacaoRh());
            } else {
                comandoDeAtualizacao.setNull(5, Types.INTEGER);
            }
            
            if (diagnosticoUsuario.getDataRealizacao() != null) {
                comandoDeAtualizacao.setObject(6, diagnosticoUsuario.getDataRealizacao());
            } else {
                comandoDeAtualizacao.setNull(6, Types.DATE);
            }
            
            comandoDeAtualizacao.setInt(7, diagnosticoUsuario.getIdDiagnostico());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar diagnóstico usuário", e);
        }
    }
    
    public boolean removerPorId(Integer idDiagnostico) {
        String sql = "DELETE FROM TB_SS_DIAGNOSTICO_USUARIO WHERE ID_DIAGNOSTICO = ?";
        
        if (!idExiste(idDiagnostico)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idDiagnostico);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover diagnóstico usuário", e);
        }
    }
    
    public DiagnosticoUsuario buscarPorId(Integer idDiagnostico) {
        String sql = "SELECT * FROM TB_SS_DIAGNOSTICO_USUARIO WHERE ID_DIAGNOSTICO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idDiagnostico);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return criarDiagnosticoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar diagnóstico usuário por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<DiagnosticoUsuario> buscarPorUsuario(Integer idUsuario) {
        ArrayList<DiagnosticoUsuario> diagnosticos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_DIAGNOSTICO_USUARIO WHERE ID_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    DiagnosticoUsuario diagnostico = criarDiagnosticoDoResultSet(rs);
                    diagnosticos.add(diagnostico);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar diagnósticos por usuário", e);
        }
        
        return diagnosticos;
    }
    
    public ArrayList<DiagnosticoUsuario> buscarPorTrilha(Integer idTrilha) {
        ArrayList<DiagnosticoUsuario> diagnosticos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_DIAGNOSTICO_USUARIO WHERE ID_TRILHA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilha);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    DiagnosticoUsuario diagnostico = criarDiagnosticoDoResultSet(rs);
                    diagnosticos.add(diagnostico);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar diagnósticos por trilha", e);
        }
        
        return diagnosticos;
    }
    
    public DiagnosticoUsuario buscarUltimoPorUsuario(Integer idUsuario) {
        String sql = "SELECT * FROM (SELECT * FROM TB_SS_DIAGNOSTICO_USUARIO WHERE ID_USUARIO = ? "
                + "ORDER BY DATA_REALIZACAO DESC NULLS LAST, ID_DIAGNOSTICO DESC) WHERE ROWNUM <= 1";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return criarDiagnosticoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar último diagnóstico do usuário", e);
        }
        
        return null;
    }
    
    public boolean idExiste(Integer idDiagnostico) {
        String sql = "SELECT 1 FROM TB_SS_DIAGNOSTICO_USUARIO WHERE ID_DIAGNOSTICO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idDiagnostico);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_DIAGNOSTICO", e);
        }
    }
    
    private DiagnosticoUsuario criarDiagnosticoDoResultSet(ResultSet rs) throws SQLException {
        DiagnosticoUsuario diagnostico = new DiagnosticoUsuario();
        diagnostico.setIdDiagnostico(rs.getInt("ID_DIAGNOSTICO"));
        diagnostico.setIdUsuario(rs.getInt("ID_USUARIO"));
        diagnostico.setIdTrilha(rs.getInt("ID_TRILHA"));
        
        if (rs.getObject("PONTUACAO_ADMIN") != null) {
            diagnostico.setPontuacaoAdmin(rs.getInt("PONTUACAO_ADMIN"));
        }
        
        if (rs.getObject("PONTUACAO_TECH") != null) {
            diagnostico.setPontuacaoTech(rs.getInt("PONTUACAO_TECH"));
        }
        
        if (rs.getObject("PONTUACAO_RH") != null) {
            diagnostico.setPontuacaoRh(rs.getInt("PONTUACAO_RH"));
        }
        
        if (rs.getObject("DATA_REALIZACAO") != null) {
            diagnostico.setDataRealizacao(rs.getObject("DATA_REALIZACAO", LocalDate.class));
        }
        
        return diagnostico;
    }
}

