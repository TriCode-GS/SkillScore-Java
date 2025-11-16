package br.com.skill.dao;

import br.com.skill.model.Prova;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProvaDAO {
    
    public void adicionar(Prova prova) {
        String sql = "INSERT INTO TB_SS_PROVA (ID_PROVA, ID_TRILHA, TITULO, DESCRICAO, QTDE_QUESTOES, DATA_CRIACAO) "
                + "VALUES(SQ_SS_PROVA.NEXTVAL, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setInt(1, prova.getIdTrilha());
            comandoDeInsercao.setString(2, prova.getTitulo());
            comandoDeInsercao.setString(3, prova.getDescricao());
            
            if (prova.getQtdeQuestoes() != null) {
                comandoDeInsercao.setInt(4, prova.getQtdeQuestoes());
            } else {
                comandoDeInsercao.setNull(4, Types.INTEGER);
            }
            
            if (prova.getDataCriacao() != null) {
                comandoDeInsercao.setObject(5, prova.getDataCriacao());
            } else {
                comandoDeInsercao.setNull(5, Types.DATE);
            }
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<Prova> obterTodasProvas() {
        ArrayList<Prova> provas = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_PROVA";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                Prova prova = new Prova();
                prova.setIdProva(rs.getInt("ID_PROVA"));
                prova.setIdTrilha(rs.getInt("ID_TRILHA"));
                prova.setTitulo(rs.getString("TITULO"));
                prova.setDescricao(rs.getString("DESCRICAO"));
                
                if (rs.getObject("QTDE_QUESTOES") != null) {
                    prova.setQtdeQuestoes(rs.getInt("QTDE_QUESTOES"));
                }
                
                if (rs.getObject("DATA_CRIACAO") != null) {
                    prova.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                }
                
                provas.add(prova);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return provas;
    }
    
    public boolean atualizar(Prova prova) {
        String sql = "UPDATE TB_SS_PROVA "
                + "SET ID_TRILHA = ?, TITULO = ?, DESCRICAO = ?, QTDE_QUESTOES = ?, DATA_CRIACAO = ? "
                + "WHERE ID_PROVA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(prova.getIdProva())) {
                return false;
            }
            
            comandoDeAtualizacao.setInt(1, prova.getIdTrilha());
            comandoDeAtualizacao.setString(2, prova.getTitulo());
            comandoDeAtualizacao.setString(3, prova.getDescricao());
            
            if (prova.getQtdeQuestoes() != null) {
                comandoDeAtualizacao.setInt(4, prova.getQtdeQuestoes());
            } else {
                comandoDeAtualizacao.setNull(4, Types.INTEGER);
            }
            
            if (prova.getDataCriacao() != null) {
                comandoDeAtualizacao.setObject(5, prova.getDataCriacao());
            } else {
                comandoDeAtualizacao.setNull(5, Types.DATE);
            }
            
            comandoDeAtualizacao.setInt(6, prova.getIdProva());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar prova", e);
        }
    }
    
    public boolean removerPorId(Integer idProva) {
        String sql = "DELETE FROM TB_SS_PROVA WHERE ID_PROVA = ?";
        
        if (!idExiste(idProva)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProva);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover prova", e);
        }
    }
    
    public Prova buscarPorId(Integer idProva) {
        String sql = "SELECT * FROM TB_SS_PROVA WHERE ID_PROVA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProva);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Prova prova = new Prova();
                    prova.setIdProva(rs.getInt("ID_PROVA"));
                    prova.setIdTrilha(rs.getInt("ID_TRILHA"));
                    prova.setTitulo(rs.getString("TITULO"));
                    prova.setDescricao(rs.getString("DESCRICAO"));
                    
                    if (rs.getObject("QTDE_QUESTOES") != null) {
                        prova.setQtdeQuestoes(rs.getInt("QTDE_QUESTOES"));
                    }
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        prova.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    return prova;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar prova por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<Prova> buscarPorTrilha(Integer idTrilha) {
        ArrayList<Prova> provas = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_PROVA WHERE ID_TRILHA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilha);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Prova prova = new Prova();
                    prova.setIdProva(rs.getInt("ID_PROVA"));
                    prova.setIdTrilha(rs.getInt("ID_TRILHA"));
                    prova.setTitulo(rs.getString("TITULO"));
                    prova.setDescricao(rs.getString("DESCRICAO"));
                    
                    if (rs.getObject("QTDE_QUESTOES") != null) {
                        prova.setQtdeQuestoes(rs.getInt("QTDE_QUESTOES"));
                    }
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        prova.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    provas.add(prova);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar provas por trilha", e);
        }
        
        return provas;
    }
    
    public boolean idExiste(Integer idProva) {
        String sql = "SELECT 1 FROM TB_SS_PROVA WHERE ID_PROVA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProva);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_PROVA", e);
        }
    }
}

