package br.com.skill.dao;

import br.com.skill.model.Questao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestaoDAO {
    
    public void adicionar(Questao questao) {
        String sql = "INSERT INTO TB_SS_QUESTAO (ID_QUESTAO, ID_PROVA, ENUNCIADO, ALTERNATIVA_A, "
                + "ALTERNATIVA_B, ALTERNATIVA_C, ALTERNATIVA_D, RESPOSTA_CORRETA) "
                + "VALUES(SQ_SS_QUESTAO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setInt(1, questao.getIdProva());
            comandoDeInsercao.setString(2, questao.getEnunciado());
            comandoDeInsercao.setString(3, questao.getAlternativaA());
            comandoDeInsercao.setString(4, questao.getAlternativaB());
            comandoDeInsercao.setString(5, questao.getAlternativaC());
            comandoDeInsercao.setString(6, questao.getAlternativaD());
            comandoDeInsercao.setString(7, questao.getRespostaCorreta());
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<Questao> obterTodasQuestoes() {
        ArrayList<Questao> questoes = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_QUESTAO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                Questao questao = new Questao();
                questao.setIdQuestao(rs.getInt("ID_QUESTAO"));
                questao.setIdProva(rs.getInt("ID_PROVA"));
                questao.setEnunciado(rs.getString("ENUNCIADO"));
                questao.setAlternativaA(rs.getString("ALTERNATIVA_A"));
                questao.setAlternativaB(rs.getString("ALTERNATIVA_B"));
                questao.setAlternativaC(rs.getString("ALTERNATIVA_C"));
                questao.setAlternativaD(rs.getString("ALTERNATIVA_D"));
                questao.setRespostaCorreta(rs.getString("RESPOSTA_CORRETA"));
                questoes.add(questao);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return questoes;
    }
    
    public boolean atualizar(Questao questao) {
        String sql = "UPDATE TB_SS_QUESTAO "
                + "SET ID_PROVA = ?, ENUNCIADO = ?, ALTERNATIVA_A = ?, ALTERNATIVA_B = ?, "
                + "ALTERNATIVA_C = ?, ALTERNATIVA_D = ?, RESPOSTA_CORRETA = ? "
                + "WHERE ID_QUESTAO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(questao.getIdQuestao())) {
                return false;
            }
            
            comandoDeAtualizacao.setInt(1, questao.getIdProva());
            comandoDeAtualizacao.setString(2, questao.getEnunciado());
            comandoDeAtualizacao.setString(3, questao.getAlternativaA());
            comandoDeAtualizacao.setString(4, questao.getAlternativaB());
            comandoDeAtualizacao.setString(5, questao.getAlternativaC());
            comandoDeAtualizacao.setString(6, questao.getAlternativaD());
            comandoDeAtualizacao.setString(7, questao.getRespostaCorreta());
            comandoDeAtualizacao.setInt(8, questao.getIdQuestao());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar questão", e);
        }
    }
    
    public boolean removerPorId(Integer idQuestao) {
        String sql = "DELETE FROM TB_SS_QUESTAO WHERE ID_QUESTAO = ?";
        
        if (!idExiste(idQuestao)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idQuestao);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover questão", e);
        }
    }
    
    public Questao buscarPorId(Integer idQuestao) {
        String sql = "SELECT * FROM TB_SS_QUESTAO WHERE ID_QUESTAO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idQuestao);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Questao questao = new Questao();
                    questao.setIdQuestao(rs.getInt("ID_QUESTAO"));
                    questao.setIdProva(rs.getInt("ID_PROVA"));
                    questao.setEnunciado(rs.getString("ENUNCIADO"));
                    questao.setAlternativaA(rs.getString("ALTERNATIVA_A"));
                    questao.setAlternativaB(rs.getString("ALTERNATIVA_B"));
                    questao.setAlternativaC(rs.getString("ALTERNATIVA_C"));
                    questao.setAlternativaD(rs.getString("ALTERNATIVA_D"));
                    questao.setRespostaCorreta(rs.getString("RESPOSTA_CORRETA"));
                    return questao;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar questão por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<Questao> buscarPorProva(Integer idProva) {
        ArrayList<Questao> questoes = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_QUESTAO WHERE ID_PROVA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idProva);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Questao questao = new Questao();
                    questao.setIdQuestao(rs.getInt("ID_QUESTAO"));
                    questao.setIdProva(rs.getInt("ID_PROVA"));
                    questao.setEnunciado(rs.getString("ENUNCIADO"));
                    questao.setAlternativaA(rs.getString("ALTERNATIVA_A"));
                    questao.setAlternativaB(rs.getString("ALTERNATIVA_B"));
                    questao.setAlternativaC(rs.getString("ALTERNATIVA_C"));
                    questao.setAlternativaD(rs.getString("ALTERNATIVA_D"));
                    questao.setRespostaCorreta(rs.getString("RESPOSTA_CORRETA"));
                    questoes.add(questao);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar questões por prova", e);
        }
        
        return questoes;
    }
    
    public boolean idExiste(Integer idQuestao) {
        String sql = "SELECT 1 FROM TB_SS_QUESTAO WHERE ID_QUESTAO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idQuestao);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_QUESTAO", e);
        }
    }
}

