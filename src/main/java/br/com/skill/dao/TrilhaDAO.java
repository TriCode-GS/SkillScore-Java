package br.com.skill.dao;

import br.com.skill.model.Trilha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class TrilhaDAO {
    
    public void adicionar(Trilha trilha) {
        String sql = "INSERT INTO TB_SS_TRILHA (ID_TRILHA, NOME_TRILHA, DATA_CRIACAO, NUM_FASES) "
                + "VALUES(SQ_SS_TRILHA.NEXTVAL, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setString(1, trilha.getNomeTrilha());
            
            if (trilha.getDataCriacao() != null) {
                comandoDeInsercao.setObject(2, trilha.getDataCriacao());
            } else {
                comandoDeInsercao.setNull(2, Types.DATE);
            }
            
            if (trilha.getNumFases() != null) {
                comandoDeInsercao.setInt(3, trilha.getNumFases());
            } else {
                comandoDeInsercao.setNull(3, Types.INTEGER);
            }
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<Trilha> obterTodasTrilhas() {
        ArrayList<Trilha> trilhas = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_TRILHA";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                Trilha trilha = new Trilha();
                trilha.setIdTrilha(rs.getInt("ID_TRILHA"));
                
                
                trilha.setNomeTrilha(rs.getString("NOME_TRILHA"));
                
                if (rs.getObject("DATA_CRIACAO") != null) {
                    trilha.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                }
                
                if (rs.getObject("NUM_FASES") != null) {
                    trilha.setNumFases(rs.getInt("NUM_FASES"));
                }
                trilhas.add(trilha);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return trilhas;
    }
    
    public boolean atualizar(Trilha trilha) {
        String sql = "UPDATE TB_SS_TRILHA "
                + "SET NOME_TRILHA = ?, DATA_CRIACAO = ?, NUM_FASES = ? "
                + "WHERE ID_TRILHA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(trilha.getIdTrilha())) {
                return false;
            }
            
            comandoDeAtualizacao.setString(1, trilha.getNomeTrilha());
            
            if (trilha.getDataCriacao() != null) {
                comandoDeAtualizacao.setObject(2, trilha.getDataCriacao());
            } else {
                comandoDeAtualizacao.setNull(2, Types.DATE);
            }
            
            if (trilha.getNumFases() != null) {
                comandoDeAtualizacao.setInt(3, trilha.getNumFases());
            } else {
                comandoDeAtualizacao.setNull(3, Types.INTEGER);
            }
            comandoDeAtualizacao.setInt(4, trilha.getIdTrilha());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar trilha", e);
        }
    }
    
    public boolean removerPorId(Integer idTrilha) {
        String sql = "DELETE FROM TB_SS_TRILHA WHERE ID_TRILHA = ?";
        
        if (!idExiste(idTrilha)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilha);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover trilha", e);
        }
    }
    
    public Trilha buscarPorId(Integer idTrilha) {
        String sql = "SELECT * FROM TB_SS_TRILHA WHERE ID_TRILHA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilha);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Trilha trilha = new Trilha();
                    trilha.setIdTrilha(rs.getInt("ID_TRILHA"));
                    
                    
                    trilha.setNomeTrilha(rs.getString("NOME_TRILHA"));
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        trilha.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    if (rs.getObject("NUM_FASES") != null) {
                        trilha.setNumFases(rs.getInt("NUM_FASES"));
                    }
                    return trilha;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<Trilha> buscarPorNome(String nomeTrilha) {
        ArrayList<Trilha> trilhas = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_TRILHA WHERE UPPER(NOME_TRILHA) LIKE UPPER(?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, "%" + nomeTrilha + "%");
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Trilha trilha = new Trilha();
                    trilha.setIdTrilha(rs.getInt("ID_TRILHA"));
                    
                    
                    trilha.setNomeTrilha(rs.getString("NOME_TRILHA"));
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        trilha.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    if (rs.getObject("NUM_FASES") != null) {
                        trilha.setNumFases(rs.getInt("NUM_FASES"));
                    }
                    trilhas.add(trilha);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilhas por nome", e);
        }
        
        return trilhas;
    }
    
    public boolean idExiste(Integer idTrilha) {
        String sql = "SELECT 1 FROM TB_SS_TRILHA WHERE ID_TRILHA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilha);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_TRILHA", e);
        }
    }
    
    public Trilha buscarPorNomeExato(String nomeTrilha) {
        String sql = "SELECT * FROM TB_SS_TRILHA WHERE UPPER(TRIM(NOME_TRILHA)) = UPPER(TRIM(?))";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, nomeTrilha);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Trilha trilha = new Trilha();
                    trilha.setIdTrilha(rs.getInt("ID_TRILHA"));
                    trilha.setNomeTrilha(rs.getString("NOME_TRILHA"));
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        trilha.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    if (rs.getObject("NUM_FASES") != null) {
                        trilha.setNumFases(rs.getInt("NUM_FASES"));
                    }
                    return trilha;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha por nome exato", e);
        }
        
        return null;
    }
}
