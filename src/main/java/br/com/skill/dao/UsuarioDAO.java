package br.com.skill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.skill.model.Usuario;

public class UsuarioDAO {
    
    public void adicionar(Usuario usuario) {
        String sql = "INSERT INTO TB_SS_USUARIO (ID_USUARIO, ID_EMPRESA,NOME_USUARIO, "
                + "TIPO_USUARIO, AREA_ATUACAO, NIVEL_SENIORIDADE, COMPETENCIAS) "
                + "VALUES(SQ_SS_USUARIO.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            if (usuario.getIdEmpresa() != null) {
                comandoDeInsercao.setInt(1, usuario.getIdEmpresa());
            } else {
                comandoDeInsercao.setNull(1, java.sql.Types.INTEGER);
            }
            
            comandoDeInsercao.setString(2, usuario.getNomeUsuario());
            comandoDeInsercao.setString(3, usuario.getTipoUsuario());
            comandoDeInsercao.setString(4, usuario.getAreaAtuacao());
            comandoDeInsercao.setString(5, usuario.getNivelSenioridade());
            comandoDeInsercao.setString(6, usuario.getCompetencias());
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<Usuario> obterTodosUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_USUARIO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                
                if (rs.getObject("ID_EMPRESA") != null) {
                    usuario.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                }
                
                usuario.setNomeUsuario(rs.getString("NOME_USUARIO"));
                usuario.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                usuario.setAreaAtuacao(rs.getString("AREA_ATUACAO"));
                usuario.setNivelSenioridade(rs.getString("NIVEL_SENIORIDADE"));
                usuario.setCompetencias(rs.getString("COMPETENCIAS"));
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return usuarios;
    }
    
    public boolean atualizar(Usuario usuario) {
        String sql = "UPDATE TB_SS_USUARIO "
                + "SET ID_EMPRESA = ?, NOME_USUARIO = ?, TIPO_USUARIO = ?, "
                + "AREA_ATUACAO = ?, NIVEL_SENIORIDADE = ?, COMPETENCIAS = ? "
                + "WHERE ID_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(usuario.getIdUsuario())) {
                return false;
            }
            
            if (usuario.getIdEmpresa() != null) {
                comandoDeAtualizacao.setInt(1, usuario.getIdEmpresa());
            } else {
                comandoDeAtualizacao.setNull(1, java.sql.Types.INTEGER);
            }
            
            comandoDeAtualizacao.setString(2, usuario.getNomeUsuario());
            comandoDeAtualizacao.setString(3, usuario.getTipoUsuario());
            comandoDeAtualizacao.setString(4, usuario.getAreaAtuacao());
            comandoDeAtualizacao.setString(5, usuario.getNivelSenioridade());
            comandoDeAtualizacao.setString(6, usuario.getCompetencias());
            comandoDeAtualizacao.setInt(7, usuario.getIdUsuario());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }
    
    public boolean removerPorId(Integer idUsuario) {
        String sql = "DELETE FROM TB_SS_USUARIO WHERE ID_USUARIO = ?";
        
        if (!idExiste(idUsuario)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover usuário", e);
        }
    }
    
    public Usuario buscarPorId(Integer idUsuario) {
        String sql = "SELECT * FROM TB_SS_USUARIO WHERE ID_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    if (rs.getObject("ID_EMPRESA") != null) {
                        usuario.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                    }
                    
                    usuario.setNomeUsuario(rs.getString("NOME_USUARIO"));
                    usuario.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                    usuario.setAreaAtuacao(rs.getString("AREA_ATUACAO"));
                    usuario.setNivelSenioridade(rs.getString("NIVEL_SENIORIDADE"));
                    usuario.setCompetencias(rs.getString("COMPETENCIAS"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }
        
        return null;
    }
    
    
    public ArrayList<Usuario> buscarPorEmpresa(Integer idEmpresa) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_USUARIO WHERE ID_EMPRESA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idEmpresa);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    usuario.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                    
                    usuario.setNomeUsuario(rs.getString("NOME_USUARIO"));
                    usuario.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                    usuario.setAreaAtuacao(rs.getString("AREA_ATUACAO"));
                    usuario.setNivelSenioridade(rs.getString("NIVEL_SENIORIDADE"));
                    usuario.setCompetencias(rs.getString("COMPETENCIAS"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários por empresa", e);
        }
        
        return usuarios;
    }
    
    
    public ArrayList<Usuario> buscarPorTipoUsuario(String tipoUsuario) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_USUARIO WHERE TIPO_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, tipoUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("ID_USUARIO"));
                    
                    if (rs.getObject("ID_EMPRESA") != null) {
                        usuario.setIdEmpresa(rs.getInt("ID_EMPRESA"));
                    }
                    
                    usuario.setNomeUsuario(rs.getString("NOME_USUARIO"));
                    usuario.setTipoUsuario(rs.getString("TIPO_USUARIO"));
                    usuario.setAreaAtuacao(rs.getString("AREA_ATUACAO"));
                    usuario.setNivelSenioridade(rs.getString("NIVEL_SENIORIDADE"));
                    usuario.setCompetencias(rs.getString("COMPETENCIAS"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários por tipo", e);
        }
        
        return usuarios;
    }
    
    public boolean atualizarIdEmpresa(Integer idUsuario, Integer idEmpresa) {
        String sql = "UPDATE TB_SS_USUARIO SET ID_EMPRESA = ? WHERE ID_USUARIO = ?";
        
        if (!idExiste(idUsuario)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            if (idEmpresa != null) {
                st.setInt(1, idEmpresa);
            } else {
                st.setNull(1, java.sql.Types.INTEGER);
            }
            
            st.setInt(2, idUsuario);
            
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ID_EMPRESA do usuário", e);
        }
    }
    
    public boolean idExiste(Integer idUsuario) {
        String sql = "SELECT 1 FROM TB_SS_USUARIO WHERE ID_USUARIO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_USUARIO", e);
        }
    }
}
