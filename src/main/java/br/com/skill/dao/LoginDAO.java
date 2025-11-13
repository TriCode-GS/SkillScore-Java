package br.com.skill.dao;

import br.com.skill.model.Login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class LoginDAO {
    
    public void adicionar(Login login) {
        String sql = "INSERT INTO TB_SS_LOGIN (ID_LOGIN, ID_USUARIO, EMAIL, SENHA, DATA_CRIACAO) "
                + "VALUES(SQ_SS_LOGIN.NEXTVAL, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setInt(1, login.getIdUsuario());
            comandoDeInsercao.setString(2, login.getEmail());
            comandoDeInsercao.setString(3, login.getSenha());
            
            if (login.getDataCriacao() != null) {
                comandoDeInsercao.setObject(4, login.getDataCriacao());
            } else {
                comandoDeInsercao.setNull(4, Types.DATE);
            }
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<Login> obterTodosLogins() {
        ArrayList<Login> logins = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_LOGIN";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                Login login = new Login();
                login.setIdLogin(rs.getInt("ID_LOGIN"));
                
                login.setIdUsuario(rs.getInt("ID_USUARIO"));
                
                login.setEmail(rs.getString("EMAIL"));
                login.setSenha(rs.getString("SENHA"));
                
                if (rs.getObject("DATA_CRIACAO") != null) {
                    login.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                }
                
                logins.add(login);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return logins;
    }
    
    public boolean atualizar(Login login) {
        String sql = "UPDATE TB_SS_LOGIN "
                + "SET ID_USUARIO = ?, EMAIL = ?, SENHA = ?, DATA_CRIACAO = ? "
                + "WHERE ID_LOGIN = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(login.getIdLogin())) {
                return false;
            }
            
            comandoDeAtualizacao.setInt(1, login.getIdUsuario());
            comandoDeAtualizacao.setString(2, login.getEmail());
            comandoDeAtualizacao.setString(3, login.getSenha());
            
            if (login.getDataCriacao() != null) {
                comandoDeAtualizacao.setObject(4, login.getDataCriacao());
            } else {
                comandoDeAtualizacao.setNull(4, Types.DATE);
            }
            
            comandoDeAtualizacao.setInt(5, login.getIdLogin());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar login", e);
        }
    }
    
    public boolean removerPorId(Integer idLogin) {
        String sql = "DELETE FROM TB_SS_LOGIN WHERE ID_LOGIN = ?";
        
        if (!idExiste(idLogin)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idLogin);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover login", e);
        }
    }
    
    public Login buscarPorId(Integer idLogin) {
        String sql = "SELECT * FROM TB_SS_LOGIN WHERE ID_LOGIN = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idLogin);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Login login = new Login();
                    login.setIdLogin(rs.getInt("ID_LOGIN"));
                    
                    login.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    login.setEmail(rs.getString("EMAIL"));
                    login.setSenha(rs.getString("SENHA"));
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        login.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    return login;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar login por ID", e);
        }
        
        return null;
    }
    
    public Login buscarPorEmail(String email) {
        String sql = "SELECT * FROM TB_SS_LOGIN WHERE EMAIL = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, email);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Login login = new Login();
                    login.setIdLogin(rs.getInt("ID_LOGIN"));
                    
                    login.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    login.setEmail(rs.getString("EMAIL"));
                    login.setSenha(rs.getString("SENHA"));
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        login.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    return login;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar login por email", e);
        }
        
        return null;
    }
    
    public ArrayList<Login> buscarPorUsuario(Integer idUsuario) {
        ArrayList<Login> logins = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_LOGIN WHERE ID_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Login login = new Login();
                    login.setIdLogin(rs.getInt("ID_LOGIN"));
                    
                    login.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    login.setEmail(rs.getString("EMAIL"));
                    login.setSenha(rs.getString("SENHA"));
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        login.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    logins.add(login);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar logins por usuário", e);
        }
        
        return logins;
    }
    
    public Login buscarPorEmailESenha(String email, String senha) {
        String sql = "SELECT * FROM TB_SS_LOGIN WHERE EMAIL = ? AND SENHA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, email);
            st.setString(2, senha);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Login login = new Login();
                    login.setIdLogin(rs.getInt("ID_LOGIN"));
                    
                    login.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    login.setEmail(rs.getString("EMAIL"));
                    login.setSenha(rs.getString("SENHA"));
                    
                    if (rs.getObject("DATA_CRIACAO") != null) {
                        login.setDataCriacao(rs.getObject("DATA_CRIACAO", LocalDate.class));
                    }
                    
                    return login;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar login por email e senha", e);
        }
        
        return null;
    }
    
    public boolean idExiste(Integer idLogin) {
        String sql = "SELECT 1 FROM TB_SS_LOGIN WHERE ID_LOGIN = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idLogin);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_LOGIN", e);
        }
    }
}
