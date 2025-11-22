package br.com.skill.dao;

import br.com.skill.model.TrilhaCurso;
import br.com.skill.model.TrilhaCursoCompleto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class TrilhaCursoDAO {
    
    public void adicionar(TrilhaCurso trilhaCurso) {
        String sql = "INSERT INTO TB_SS_TRILHA_CURSO (ID_TRILHA_CURSO, ID_TRILHA, ID_CURSO, "
                + "ORDEM_FASE, STATUS_FASE, DATA_CONCLUSAO) "
                + "VALUES(SQ_SS_TRILHA_CURSO.NEXTVAL, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setInt(1, trilhaCurso.getIdTrilha());
            comandoDeInsercao.setInt(2, trilhaCurso.getIdCurso());
            comandoDeInsercao.setInt(3, trilhaCurso.getOrdemFase());
            comandoDeInsercao.setString(4, trilhaCurso.getStatusFase());
            
            if (trilhaCurso.getDataConclusao() != null) {
                comandoDeInsercao.setObject(5, trilhaCurso.getDataConclusao());
            } else {
                comandoDeInsercao.setNull(5, Types.DATE);
            }
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<TrilhaCurso> obterTodosTrilhaCursos() {
        ArrayList<TrilhaCurso> trilhaCursos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_TRILHA_CURSO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                TrilhaCurso trilhaCurso = new TrilhaCurso();
                trilhaCurso.setIdTrilhaCurso(rs.getInt("ID_TRILHA_CURSO"));
                
                trilhaCurso.setIdTrilha(rs.getInt("ID_TRILHA"));
                trilhaCurso.setIdCurso(rs.getInt("ID_CURSO"));
                
                trilhaCurso.setOrdemFase(rs.getInt("ORDEM_FASE"));
                trilhaCurso.setStatusFase(rs.getString("STATUS_FASE"));
                
                if (rs.getObject("DATA_CONCLUSAO") != null) {
                    trilhaCurso.setDataConclusao(rs.getObject("DATA_CONCLUSAO", LocalDate.class));
                }
                
                trilhaCursos.add(trilhaCurso);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return trilhaCursos;
    }
    
    public boolean atualizar(TrilhaCurso trilhaCurso) {
        String sql = "UPDATE TB_SS_TRILHA_CURSO "
                + "SET ID_TRILHA = ?, ID_CURSO = ?, ORDEM_FASE = ?, STATUS_FASE = ?, DATA_CONCLUSAO = ? "
                + "WHERE ID_TRILHA_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(trilhaCurso.getIdTrilhaCurso())) {
                return false;
            }
            
            comandoDeAtualizacao.setInt(1, trilhaCurso.getIdTrilha());
            comandoDeAtualizacao.setInt(2, trilhaCurso.getIdCurso());
            comandoDeAtualizacao.setInt(3, trilhaCurso.getOrdemFase());
            comandoDeAtualizacao.setString(4, trilhaCurso.getStatusFase());
            
            if (trilhaCurso.getDataConclusao() != null) {
                comandoDeAtualizacao.setObject(5, trilhaCurso.getDataConclusao());
            } else {
                comandoDeAtualizacao.setNull(5, Types.DATE);
            }
            
            comandoDeAtualizacao.setInt(6, trilhaCurso.getIdTrilhaCurso());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar trilha-curso", e);
        }
    }
    
    public boolean removerPorId(Integer idTrilhaCurso) {
        String sql = "DELETE FROM TB_SS_TRILHA_CURSO WHERE ID_TRILHA_CURSO = ?";
        
        if (!idExiste(idTrilhaCurso)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilhaCurso);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover trilha-curso", e);
        }
    }
    
    public TrilhaCurso buscarPorId(Integer idTrilhaCurso) {
        String sql = "SELECT * FROM TB_SS_TRILHA_CURSO WHERE ID_TRILHA_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilhaCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    TrilhaCurso trilhaCurso = new TrilhaCurso();
                    trilhaCurso.setIdTrilhaCurso(rs.getInt("ID_TRILHA_CURSO"));
                    
                    trilhaCurso.setIdTrilha(rs.getInt("ID_TRILHA"));
                    trilhaCurso.setIdCurso(rs.getInt("ID_CURSO"));
                    
                    trilhaCurso.setOrdemFase(rs.getInt("ORDEM_FASE"));
                    trilhaCurso.setStatusFase(rs.getString("STATUS_FASE"));
                    
                    if (rs.getObject("DATA_CONCLUSAO") != null) {
                        trilhaCurso.setDataConclusao(rs.getObject("DATA_CONCLUSAO", LocalDate.class));
                    }
                    
                    return trilhaCurso;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha-curso por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<TrilhaCurso> buscarPorTrilha(Integer idTrilha) {
        ArrayList<TrilhaCurso> trilhaCursos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_TRILHA_CURSO WHERE ID_TRILHA = ? ORDER BY ORDEM_FASE ASC";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilha);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    TrilhaCurso trilhaCurso = new TrilhaCurso();
                    trilhaCurso.setIdTrilhaCurso(rs.getInt("ID_TRILHA_CURSO"));
                    
                    trilhaCurso.setIdTrilha(rs.getInt("ID_TRILHA"));
                    trilhaCurso.setIdCurso(rs.getInt("ID_CURSO"));
                    
                    trilhaCurso.setOrdemFase(rs.getInt("ORDEM_FASE"));
                    trilhaCurso.setStatusFase(rs.getString("STATUS_FASE"));
                    
                    if (rs.getObject("DATA_CONCLUSAO") != null) {
                        trilhaCurso.setDataConclusao(rs.getObject("DATA_CONCLUSAO", LocalDate.class));
                    }
                    
                    trilhaCursos.add(trilhaCurso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha-cursos por trilha", e);
        }
        
        return trilhaCursos;
    }
    
    public ArrayList<TrilhaCurso> buscarPorTrilhaOrdenadoPorFase(Integer idTrilha) {
        return buscarPorTrilha(idTrilha);
    }
    
    public ArrayList<TrilhaCurso> buscarPorCurso(Integer idCurso) {
        ArrayList<TrilhaCurso> trilhaCursos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_TRILHA_CURSO WHERE ID_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    TrilhaCurso trilhaCurso = new TrilhaCurso();
                    trilhaCurso.setIdTrilhaCurso(rs.getInt("ID_TRILHA_CURSO"));
                    
                    trilhaCurso.setIdTrilha(rs.getInt("ID_TRILHA"));
                    trilhaCurso.setIdCurso(rs.getInt("ID_CURSO"));
                    
                    trilhaCurso.setOrdemFase(rs.getInt("ORDEM_FASE"));
                    trilhaCurso.setStatusFase(rs.getString("STATUS_FASE"));
                    
                    if (rs.getObject("DATA_CONCLUSAO") != null) {
                        trilhaCurso.setDataConclusao(rs.getObject("DATA_CONCLUSAO", LocalDate.class));
                    }
                    
                    trilhaCursos.add(trilhaCurso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha-cursos por curso", e);
        }
        
        return trilhaCursos;
    }
    
    public ArrayList<TrilhaCurso> buscarPorStatusFase(String statusFase) {
        ArrayList<TrilhaCurso> trilhaCursos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_TRILHA_CURSO WHERE STATUS_FASE = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, statusFase);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    TrilhaCurso trilhaCurso = new TrilhaCurso();
                    trilhaCurso.setIdTrilhaCurso(rs.getInt("ID_TRILHA_CURSO"));
                    
                    trilhaCurso.setIdTrilha(rs.getInt("ID_TRILHA"));
                    trilhaCurso.setIdCurso(rs.getInt("ID_CURSO"));
                    
                    trilhaCurso.setOrdemFase(rs.getInt("ORDEM_FASE"));
                    trilhaCurso.setStatusFase(rs.getString("STATUS_FASE"));
                    
                    if (rs.getObject("DATA_CONCLUSAO") != null) {
                        trilhaCurso.setDataConclusao(rs.getObject("DATA_CONCLUSAO", LocalDate.class));
                    }
                    
                    trilhaCursos.add(trilhaCurso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha-cursos por status", e);
        }
        
        return trilhaCursos;
    }
    
    public ArrayList<TrilhaCursoCompleto> buscarPorTrilhaComDadosCurso(Integer idTrilha) {
        ArrayList<TrilhaCursoCompleto> trilhaCursosCompletos = new ArrayList<>();
        String sql = "SELECT tc.ID_TRILHA_CURSO, tc.ID_TRILHA, tc.ID_CURSO, tc.ORDEM_FASE, " +
                     "tc.STATUS_FASE, tc.DATA_CONCLUSAO, " +
                     "c.TITULO, c.DESCRICAO, c.LINK_CURSO, c.AREA_RELACIONADA, " +
                     "c.NIVEL_RECOMENDADO, c.DURACAO_HORAS " +
                     "FROM TB_SS_TRILHA_CURSO tc " +
                     "INNER JOIN TB_SS_CURSO c ON tc.ID_CURSO = c.ID_CURSO " +
                     "WHERE tc.ID_TRILHA = ? " +
                     "ORDER BY tc.ORDEM_FASE ASC";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilha);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    TrilhaCursoCompleto trilhaCursoCompleto = new TrilhaCursoCompleto();
                    
                    trilhaCursoCompleto.setIdTrilhaCurso(rs.getInt("ID_TRILHA_CURSO"));
                    trilhaCursoCompleto.setIdTrilha(rs.getInt("ID_TRILHA"));
                    trilhaCursoCompleto.setIdCurso(rs.getInt("ID_CURSO"));
                    trilhaCursoCompleto.setOrdemFase(rs.getInt("ORDEM_FASE"));
                    trilhaCursoCompleto.setStatusFase(rs.getString("STATUS_FASE"));
                    
                    if (rs.getObject("DATA_CONCLUSAO") != null) {
                        trilhaCursoCompleto.setDataConclusao(rs.getObject("DATA_CONCLUSAO", LocalDate.class));
                    }
                    
                    trilhaCursoCompleto.setTitulo(rs.getString("TITULO"));
                    trilhaCursoCompleto.setDescricao(rs.getString("DESCRICAO"));
                    trilhaCursoCompleto.setLinkCurso(rs.getString("LINK_CURSO"));
                    trilhaCursoCompleto.setAreaRelacionada(rs.getString("AREA_RELACIONADA"));
                    trilhaCursoCompleto.setNivelRecomendado(rs.getString("NIVEL_RECOMENDADO"));
                    trilhaCursoCompleto.setDuracaoHoras(rs.getString("DURACAO_HORAS"));
                    
                    trilhaCursosCompletos.add(trilhaCursoCompleto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha-cursos com dados do curso", e);
        }
        
        return trilhaCursosCompletos;
    }
    
    public boolean atualizarStatusFase(Integer idTrilhaCurso, String statusFase) {
        String sql = "UPDATE TB_SS_TRILHA_CURSO SET STATUS_FASE = ? WHERE ID_TRILHA_CURSO = ?";
        
        if (!idExiste(idTrilhaCurso)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, statusFase);
            st.setInt(2, idTrilhaCurso);
            
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status da fase", e);
        }
    }
    
    public boolean idExiste(Integer idTrilhaCurso) {
        String sql = "SELECT 1 FROM TB_SS_TRILHA_CURSO WHERE ID_TRILHA_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilhaCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_TRILHA_CURSO", e);
        }
    }
}
