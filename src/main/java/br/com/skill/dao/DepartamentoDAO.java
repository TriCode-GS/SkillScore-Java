package br.com.skill.dao;

import br.com.skill.model.Departamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DepartamentoDAO {
    
    public void adicionar(Departamento departamento) {
        String sql = "INSERT INTO TB_SS_DEPARTAMENTO (ID_DEPARTAMENTO, ID_EMPRESA, NOME_DEPARTAMENTO, DATA_CRIACAO) "
                + "VALUES(SQ_SS_DEPARTAMENTO.NEXTVAL, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            if (departamento.getIdEmpresa() != null) {
                comandoDeInsercao.setInt(1, departamento.getIdEmpresa());
            } else {
                comandoDeInsercao.setNull(1, java.sql.Types.INTEGER);
            }
            
            comandoDeInsercao.setString(2, departamento.getNomeDepartamento());
            
            if (departamento.getDataCriacao() != null) {
                comandoDeInsercao.setDate(3, java.sql.Date.valueOf(departamento.getDataCriacao()));
            } else {
                comandoDeInsercao.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            }
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<Departamento> obterTodosDepartamentos() {
        ArrayList<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_DEPARTAMENTO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                Departamento departamento = new Departamento();
                departamento.setIdDepartamento(rs.getInt("ID_DEPARTAMENTO"));
                
                if (rs.getObject("ID_EMPRESA") != null) {
                    departamento.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                }
                
                departamento.setNomeDepartamento(rs.getString("NOME_DEPARTAMENTO"));
                
                if (rs.getDate("DATA_CRIACAO") != null) {
                    departamento.setDataCriacao(rs.getDate("DATA_CRIACAO").toLocalDate());
                }
                
                departamentos.add(departamento);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return departamentos;
    }
    
    public boolean atualizar(Departamento departamento) {
        String sql = "UPDATE TB_SS_DEPARTAMENTO "
                + "SET ID_EMPRESA = ?, NOME_DEPARTAMENTO = ?, DATA_CRIACAO = ? "
                + "WHERE ID_DEPARTAMENTO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(departamento.getIdDepartamento())) {
                return false;
            }
            
            if (departamento.getIdEmpresa() != null) {
                comandoDeAtualizacao.setInt(1, departamento.getIdEmpresa());
            } else {
                comandoDeAtualizacao.setNull(1, java.sql.Types.INTEGER);
            }
            
            comandoDeAtualizacao.setString(2, departamento.getNomeDepartamento());
            
            if (departamento.getDataCriacao() != null) {
                comandoDeAtualizacao.setDate(3, java.sql.Date.valueOf(departamento.getDataCriacao()));
            } else {
                comandoDeAtualizacao.setNull(3, java.sql.Types.DATE);
            }
            
            comandoDeAtualizacao.setInt(4, departamento.getIdDepartamento());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar departamento", e);
        }
    }
    
    public boolean removerPorId(Integer idDepartamento) {
        String sql = "DELETE FROM TB_SS_DEPARTAMENTO WHERE ID_DEPARTAMENTO = ?";
        
        if (!idExiste(idDepartamento)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idDepartamento);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover departamento", e);
        }
    }
    
    public Departamento buscarPorId(Integer idDepartamento) {
        String sql = "SELECT * FROM TB_SS_DEPARTAMENTO WHERE ID_DEPARTAMENTO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idDepartamento);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Departamento departamento = new Departamento();
                    departamento.setIdDepartamento(rs.getInt("ID_DEPARTAMENTO"));
                    
                    if (rs.getObject("ID_EMPRESA") != null) {
                        departamento.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                    }
                    
                    departamento.setNomeDepartamento(rs.getString("NOME_DEPARTAMENTO"));
                    
                    if (rs.getDate("DATA_CRIACAO") != null) {
                        departamento.setDataCriacao(rs.getDate("DATA_CRIACAO").toLocalDate());
                    }
                    
                    return departamento;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar departamento por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<Departamento> buscarPorEmpresa(Integer idEmpresa) {
        ArrayList<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_DEPARTAMENTO WHERE ID_EMPRESA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idEmpresa);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Departamento departamento = new Departamento();
                    departamento.setIdDepartamento(rs.getInt("ID_DEPARTAMENTO"));
                    
                    if (rs.getObject("ID_EMPRESA") != null) {
                        departamento.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                    }
                    
                    departamento.setNomeDepartamento(rs.getString("NOME_DEPARTAMENTO"));
                    
                    if (rs.getDate("DATA_CRIACAO") != null) {
                        departamento.setDataCriacao(rs.getDate("DATA_CRIACAO").toLocalDate());
                    }
                    
                    departamentos.add(departamento);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar departamentos por empresa", e);
        }
        
        return departamentos;
    }
    
    public boolean idExiste(Integer idDepartamento) {
        String sql = "SELECT 1 FROM TB_SS_DEPARTAMENTO WHERE ID_DEPARTAMENTO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idDepartamento);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_DEPARTAMENTO", e);
        }
    }
    
    public boolean existeUsuarioVinculado(Integer idDepartamento) {
        String sql = "SELECT 1 FROM TB_SS_USUARIO WHERE ID_DEPARTAMENTO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idDepartamento);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar usuários vinculados ao departamento", e);
        }
    }
}

