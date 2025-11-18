package br.com.skill.dao;

import br.com.skill.model.Empresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpresaDAO {
    
    public void adicionar(Empresa empresa) {
        String sql = "INSERT INTO TB_SS_EMPRESA (ID_EMPRESA, NOME_EMPRESA, CNPJ, SETOR) "
                + "VALUES(SQ_SS_EMPRESA.NEXTVAL, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setString(1, empresa.getNomeEmpresa());
            comandoDeInsercao.setString(2, empresa.getCnpj());
            comandoDeInsercao.setString(3, empresa.getSetor());
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<Empresa> obterTodasEmpresas() {
        ArrayList<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_EMPRESA";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                empresa.setNomeEmpresa(rs.getString("NOME_EMPRESA"));
                empresa.setCnpj(rs.getString("CNPJ"));
                empresa.setSetor(rs.getString("SETOR"));
                
                empresas.add(empresa);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return empresas;
    }
    
    public boolean atualizar(Empresa empresa) {
        String sql = "UPDATE TB_SS_EMPRESA "
                + "SET NOME_EMPRESA = ?, CNPJ = ?, SETOR = ? "
                + "WHERE ID_EMPRESA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(empresa.getIdEmpresa())) {
                return false;
            }
            
            comandoDeAtualizacao.setString(1, empresa.getNomeEmpresa());
            comandoDeAtualizacao.setString(2, empresa.getCnpj());
            comandoDeAtualizacao.setString(3, empresa.getSetor());
            comandoDeAtualizacao.setInt(4, empresa.getIdEmpresa());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar empresa", e);
        }
    }
    
    public boolean removerPorId(Integer idEmpresa) {
        String sql = "DELETE FROM TB_SS_EMPRESA WHERE ID_EMPRESA = ?";
        
        if (!idExiste(idEmpresa)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idEmpresa);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover empresa", e);
        }
    }
    
    public Empresa buscarPorId(Integer idEmpresa) {
        String sql = "SELECT * FROM TB_SS_EMPRESA WHERE ID_EMPRESA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idEmpresa);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                    empresa.setNomeEmpresa(rs.getString("NOME_EMPRESA"));
                    empresa.setCnpj(rs.getString("CNPJ"));
                    empresa.setSetor(rs.getString("SETOR"));
                    
                    return empresa;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empresa por ID", e);
        }
        
        return null;
    }
    
    public Empresa buscarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM TB_SS_EMPRESA WHERE CNPJ = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, cnpj);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Empresa empresa = new Empresa();
                    empresa.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                    empresa.setNomeEmpresa(rs.getString("NOME_EMPRESA"));
                    empresa.setCnpj(rs.getString("CNPJ"));
                    empresa.setSetor(rs.getString("SETOR"));
                    
                    return empresa;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empresa por CNPJ", e);
        }
        
        return null;
    }
    
    public boolean idExiste(Integer idEmpresa) {
        String sql = "SELECT 1 FROM TB_SS_EMPRESA WHERE ID_EMPRESA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idEmpresa);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_EMPRESA", e);
        }
    }
}
