package br.com.skill.dao;

import br.com.skill.model.RespostaUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RespostaUsuarioDAO {
    
    public void adicionar(RespostaUsuario respostaUsuario) {
        String sql = "INSERT INTO TB_SS_RESPOSTA_USUARIO (ID_RESPOSTA_USUARIO, ID_PROVA_USUARIO, "
                + "ID_QUESTAO, RESPOSTA, CORRETA) "
                + "VALUES(SQ_SS_RESPOSTA_USUARIO.NEXTVAL, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setInt(1, respostaUsuario.getIdProvaUsuario());
            comandoDeInsercao.setInt(2, respostaUsuario.getIdQuestao());
            comandoDeInsercao.setString(3, respostaUsuario.getResposta());
            comandoDeInsercao.setString(4, respostaUsuario.getCorreta());
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<RespostaUsuario> obterTodasRespostasUsuario() {
        ArrayList<RespostaUsuario> respostasUsuario = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_RESPOSTA_USUARIO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                RespostaUsuario respostaUsuario = new RespostaUsuario();
                respostaUsuario.setIdRespostaUsuario(rs.getInt("ID_RESPOSTA_USUARIO"));
                respostaUsuario.setIdProvaUsuario(rs.getInt("ID_PROVA_USUARIO"));
                respostaUsuario.setIdQuestao(rs.getInt("ID_QUESTAO"));
                respostaUsuario.setResposta(rs.getString("RESPOSTA"));
                respostaUsuario.setCorreta(rs.getString("CORRETA"));
                respostasUsuario.add(respostaUsuario);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return respostasUsuario;
    }
    
    public boolean atualizar(RespostaUsuario respostaUsuario) {
        String sql = "UPDATE TB_SS_RESPOSTA_USUARIO "
                + "SET ID_PROVA_USUARIO = ?, ID_QUESTAO = ?, RESPOSTA = ?, CORRETA = ? "
                + "WHERE ID_RESPOSTA_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(respostaUsuario.getIdRespostaUsuario())) {
                return false;
            }
            
            comandoDeAtualizacao.setInt(1, respostaUsuario.getIdProvaUsuario());
            comandoDeAtualizacao.setInt(2, respostaUsuario.getIdQuestao());
            comandoDeAtualizacao.setString(3, respostaUsuario.getResposta());
            comandoDeAtualizacao.setString(4, respostaUsuario.getCorreta());
            comandoDeAtualizacao.setInt(5, respostaUsuario.getIdRespostaUsuario());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar resposta usuário", e);
        }
    }
    
    public boolean removerPorId(Integer idRespostaUsuario) {
        String sql = "DELETE FROM TB_SS_RESPOSTA_USUARIO WHERE ID_RESPOSTA_USUARIO = ?";
        
        if (!idExiste(idRespostaUsuario)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idRespostaUsuario);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover resposta usuário", e);
        }
    }
    
    public RespostaUsuario buscarPorId(Integer idRespostaUsuario) {
        String sql = "SELECT * FROM TB_SS_RESPOSTA_USUARIO WHERE ID_RESPOSTA_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idRespostaUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    RespostaUsuario respostaUsuario = new RespostaUsuario();
                    respostaUsuario.setIdRespostaUsuario(rs.getInt("ID_RESPOSTA_USUARIO"));
                    respostaUsuario.setIdProvaUsuario(rs.getInt("ID_PROVA_USUARIO"));
                    respostaUsuario.setIdQuestao(rs.getInt("ID_QUESTAO"));
                    respostaUsuario.setResposta(rs.getString("RESPOSTA"));
                    respostaUsuario.setCorreta(rs.getString("CORRETA"));
                    return respostaUsuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar resposta usuário por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<RespostaUsuario> buscarPorProvaUsuario(Integer idProvaUsuario) {
        ArrayList<RespostaUsuario> respostasUsuario = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_RESPOSTA_USUARIO WHERE ID_PROVA_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProvaUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    RespostaUsuario respostaUsuario = new RespostaUsuario();
                    respostaUsuario.setIdRespostaUsuario(rs.getInt("ID_RESPOSTA_USUARIO"));
                    respostaUsuario.setIdProvaUsuario(rs.getInt("ID_PROVA_USUARIO"));
                    respostaUsuario.setIdQuestao(rs.getInt("ID_QUESTAO"));
                    respostaUsuario.setResposta(rs.getString("RESPOSTA"));
                    respostaUsuario.setCorreta(rs.getString("CORRETA"));
                    respostasUsuario.add(respostaUsuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar respostas usuário por prova usuário", e);
        }
        
        return respostasUsuario;
    }
    
    public ArrayList<RespostaUsuario> buscarPorQuestao(Integer idQuestao) {
        ArrayList<RespostaUsuario> respostasUsuario = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_RESPOSTA_USUARIO WHERE ID_QUESTAO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idQuestao);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    RespostaUsuario respostaUsuario = new RespostaUsuario();
                    respostaUsuario.setIdRespostaUsuario(rs.getInt("ID_RESPOSTA_USUARIO"));
                    respostaUsuario.setIdProvaUsuario(rs.getInt("ID_PROVA_USUARIO"));
                    respostaUsuario.setIdQuestao(rs.getInt("ID_QUESTAO"));
                    respostaUsuario.setResposta(rs.getString("RESPOSTA"));
                    respostaUsuario.setCorreta(rs.getString("CORRETA"));
                    respostasUsuario.add(respostaUsuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar respostas usuário por questão", e);
        }
        
        return respostasUsuario;
    }
    
    public boolean idExiste(Integer idRespostaUsuario) {
        String sql = "SELECT 1 FROM TB_SS_RESPOSTA_USUARIO WHERE ID_RESPOSTA_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idRespostaUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_RESPOSTA_USUARIO", e);
        }
    }
}

