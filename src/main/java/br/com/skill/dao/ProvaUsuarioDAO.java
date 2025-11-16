package br.com.skill.dao;

import br.com.skill.model.ProvaUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProvaUsuarioDAO {
    
    public void adicionar(ProvaUsuario provaUsuario) {
        String sql = "INSERT INTO TB_SS_PROVA_USUARIO (ID_PROVA_USUARIO, ID_PROVA, ID_USUARIO, "
                + "NOTA_OBTIDA, PERCENTUAL_ACERTO, APROVADO, DATA_REALIZACAO) "
                + "VALUES(SQ_SS_PROVA_USUARIO.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setInt(1, provaUsuario.getIdProva());
            comandoDeInsercao.setInt(2, provaUsuario.getIdUsuario());
            
            if (provaUsuario.getNotaObtida() != null) {
                comandoDeInsercao.setDouble(3, provaUsuario.getNotaObtida());
            } else {
                comandoDeInsercao.setNull(3, Types.DOUBLE);
            }
            
            if (provaUsuario.getPercentualAcerto() != null) {
                comandoDeInsercao.setDouble(4, provaUsuario.getPercentualAcerto());
            } else {
                comandoDeInsercao.setNull(4, Types.DOUBLE);
            }
            
            comandoDeInsercao.setString(5, provaUsuario.getAprovado());
            
            if (provaUsuario.getDataRealizacao() != null) {
                comandoDeInsercao.setObject(6, provaUsuario.getDataRealizacao());
            } else {
                comandoDeInsercao.setNull(6, Types.DATE);
            }
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<ProvaUsuario> obterTodasProvasUsuario() {
        ArrayList<ProvaUsuario> provasUsuario = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_PROVA_USUARIO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                ProvaUsuario provaUsuario = new ProvaUsuario();
                provaUsuario.setIdProvaUsuario(rs.getInt("ID_PROVA_USUARIO"));
                provaUsuario.setIdProva(rs.getInt("ID_PROVA"));
                provaUsuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                
                if (rs.getObject("NOTA_OBTIDA") != null) {
                    provaUsuario.setNotaObtida(rs.getDouble("NOTA_OBTIDA"));
                }
                
                if (rs.getObject("PERCENTUAL_ACERTO") != null) {
                    provaUsuario.setPercentualAcerto(rs.getDouble("PERCENTUAL_ACERTO"));
                }
                
                provaUsuario.setAprovado(rs.getString("APROVADO"));
                
                if (rs.getObject("DATA_REALIZACAO") != null) {
                    provaUsuario.setDataRealizacao(rs.getObject("DATA_REALIZACAO", LocalDate.class));
                }
                
                provasUsuario.add(provaUsuario);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return provasUsuario;
    }
    
    public boolean atualizar(ProvaUsuario provaUsuario) {
        String sql = "UPDATE TB_SS_PROVA_USUARIO "
                + "SET ID_PROVA = ?, ID_USUARIO = ?, NOTA_OBTIDA = ?, PERCENTUAL_ACERTO = ?, "
                + "APROVADO = ?, DATA_REALIZACAO = ? "
                + "WHERE ID_PROVA_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(provaUsuario.getIdProvaUsuario())) {
                return false;
            }
            
            comandoDeAtualizacao.setInt(1, provaUsuario.getIdProva());
            comandoDeAtualizacao.setInt(2, provaUsuario.getIdUsuario());
            
            if (provaUsuario.getNotaObtida() != null) {
                comandoDeAtualizacao.setDouble(3, provaUsuario.getNotaObtida());
            } else {
                comandoDeAtualizacao.setNull(3, Types.DOUBLE);
            }
            
            if (provaUsuario.getPercentualAcerto() != null) {
                comandoDeAtualizacao.setDouble(4, provaUsuario.getPercentualAcerto());
            } else {
                comandoDeAtualizacao.setNull(4, Types.DOUBLE);
            }
            
            comandoDeAtualizacao.setString(5, provaUsuario.getAprovado());
            
            if (provaUsuario.getDataRealizacao() != null) {
                comandoDeAtualizacao.setObject(6, provaUsuario.getDataRealizacao());
            } else {
                comandoDeAtualizacao.setNull(6, Types.DATE);
            }
            
            comandoDeAtualizacao.setInt(7, provaUsuario.getIdProvaUsuario());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar prova usuário", e);
        }
    }
    
    public boolean removerPorId(Integer idProvaUsuario) {
        String sql = "DELETE FROM TB_SS_PROVA_USUARIO WHERE ID_PROVA_USUARIO = ?";
        
        if (!idExiste(idProvaUsuario)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProvaUsuario);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover prova usuário", e);
        }
    }
    
    public ProvaUsuario buscarPorId(Integer idProvaUsuario) {
        String sql = "SELECT * FROM TB_SS_PROVA_USUARIO WHERE ID_PROVA_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProvaUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    ProvaUsuario provaUsuario = new ProvaUsuario();
                    provaUsuario.setIdProvaUsuario(rs.getInt("ID_PROVA_USUARIO"));
                    provaUsuario.setIdProva(rs.getInt("ID_PROVA"));
                    provaUsuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    if (rs.getObject("NOTA_OBTIDA") != null) {
                        provaUsuario.setNotaObtida(rs.getDouble("NOTA_OBTIDA"));
                    }
                    
                    if (rs.getObject("PERCENTUAL_ACERTO") != null) {
                        provaUsuario.setPercentualAcerto(rs.getDouble("PERCENTUAL_ACERTO"));
                    }
                    
                    provaUsuario.setAprovado(rs.getString("APROVADO"));
                    
                    if (rs.getObject("DATA_REALIZACAO") != null) {
                        provaUsuario.setDataRealizacao(rs.getObject("DATA_REALIZACAO", LocalDate.class));
                    }
                    
                    return provaUsuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar prova usuário por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<ProvaUsuario> buscarPorProva(Integer idProva) {
        ArrayList<ProvaUsuario> provasUsuario = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_PROVA_USUARIO WHERE ID_PROVA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProva);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ProvaUsuario provaUsuario = new ProvaUsuario();
                    provaUsuario.setIdProvaUsuario(rs.getInt("ID_PROVA_USUARIO"));
                    provaUsuario.setIdProva(rs.getInt("ID_PROVA"));
                    provaUsuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    if (rs.getObject("NOTA_OBTIDA") != null) {
                        provaUsuario.setNotaObtida(rs.getDouble("NOTA_OBTIDA"));
                    }
                    
                    if (rs.getObject("PERCENTUAL_ACERTO") != null) {
                        provaUsuario.setPercentualAcerto(rs.getDouble("PERCENTUAL_ACERTO"));
                    }
                    
                    provaUsuario.setAprovado(rs.getString("APROVADO"));
                    
                    if (rs.getObject("DATA_REALIZACAO") != null) {
                        provaUsuario.setDataRealizacao(rs.getObject("DATA_REALIZACAO", LocalDate.class));
                    }
                    
                    provasUsuario.add(provaUsuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar provas usuário por prova", e);
        }
        
        return provasUsuario;
    }
    
    public ArrayList<ProvaUsuario> buscarPorUsuario(Integer idUsuario) {
        ArrayList<ProvaUsuario> provasUsuario = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_PROVA_USUARIO WHERE ID_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    ProvaUsuario provaUsuario = new ProvaUsuario();
                    provaUsuario.setIdProvaUsuario(rs.getInt("ID_PROVA_USUARIO"));
                    provaUsuario.setIdProva(rs.getInt("ID_PROVA"));
                    provaUsuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    if (rs.getObject("NOTA_OBTIDA") != null) {
                        provaUsuario.setNotaObtida(rs.getDouble("NOTA_OBTIDA"));
                    }
                    
                    if (rs.getObject("PERCENTUAL_ACERTO") != null) {
                        provaUsuario.setPercentualAcerto(rs.getDouble("PERCENTUAL_ACERTO"));
                    }
                    
                    provaUsuario.setAprovado(rs.getString("APROVADO"));
                    
                    if (rs.getObject("DATA_REALIZACAO") != null) {
                        provaUsuario.setDataRealizacao(rs.getObject("DATA_REALIZACAO", LocalDate.class));
                    }
                    
                    provasUsuario.add(provaUsuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar provas usuário por usuário", e);
        }
        
        return provasUsuario;
    }
    
    public boolean idExiste(Integer idProvaUsuario) {
        String sql = "SELECT 1 FROM TB_SS_PROVA_USUARIO WHERE ID_PROVA_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProvaUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_PROVA_USUARIO", e);
        }
    }
}

