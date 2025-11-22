package br.com.skill.dao;

import br.com.skill.model.UsuarioTrilhaCurso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioTrilhaCursoDAO {
    
    public void adicionar(UsuarioTrilhaCurso usuarioTrilhaCurso) {
        String sql = "INSERT INTO TB_SS_USUARIO_TRILHA_CURSO (ID_USUARIO_TRILHA_CURSO, ID_USUARIO, ID_TRILHA_CURSO, "
                + "STATUS_FASE, DATA_CONCLUSAO) "
                + "VALUES(SQ_SS_USUARIO_TRILHA_CURSO.NEXTVAL, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setInt(1, usuarioTrilhaCurso.getIdUsuario());
            comandoDeInsercao.setInt(2, usuarioTrilhaCurso.getIdTrilhaCurso());
            
            if (usuarioTrilhaCurso.getStatusFase() != null) {
                comandoDeInsercao.setString(3, usuarioTrilhaCurso.getStatusFase());
            } else {
                comandoDeInsercao.setNull(3, Types.VARCHAR);
            }
            
            if (usuarioTrilhaCurso.getDataConclusao() != null) {
                comandoDeInsercao.setObject(4, usuarioTrilhaCurso.getDataConclusao());
            } else {
                comandoDeInsercao.setNull(4, Types.DATE);
            }
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar usuário-trilha-curso", e);
        }
    }
    
    public ArrayList<UsuarioTrilhaCurso> obterTodos() {
        ArrayList<UsuarioTrilhaCurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_USUARIO_TRILHA_CURSO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    lista.add(criarUsuarioTrilhaCursoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter todos os usuário-trilha-curso", e);
        }
        
        return lista;
    }
    
    public boolean atualizar(UsuarioTrilhaCurso usuarioTrilhaCurso) {
        String sql = "UPDATE TB_SS_USUARIO_TRILHA_CURSO "
                + "SET ID_USUARIO = ?, ID_TRILHA_CURSO = ?, STATUS_FASE = ?, DATA_CONCLUSAO = ? "
                + "WHERE ID_USUARIO_TRILHA_CURSO = ?";
        
        if (!idExiste(usuarioTrilhaCurso.getIdUsuarioTrilhaCurso())) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, usuarioTrilhaCurso.getIdUsuario());
            st.setInt(2, usuarioTrilhaCurso.getIdTrilhaCurso());
            
            if (usuarioTrilhaCurso.getStatusFase() != null) {
                st.setString(3, usuarioTrilhaCurso.getStatusFase());
            } else {
                st.setNull(3, Types.VARCHAR);
            }
            
            if (usuarioTrilhaCurso.getDataConclusao() != null) {
                st.setObject(4, usuarioTrilhaCurso.getDataConclusao());
            } else {
                st.setNull(4, Types.DATE);
            }
            
            st.setInt(5, usuarioTrilhaCurso.getIdUsuarioTrilhaCurso());
            
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário-trilha-curso", e);
        }
    }
    
    public boolean removerPorId(Integer idUsuarioTrilhaCurso) {
        String sql = "DELETE FROM TB_SS_USUARIO_TRILHA_CURSO WHERE ID_USUARIO_TRILHA_CURSO = ?";
        
        if (!idExiste(idUsuarioTrilhaCurso)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuarioTrilhaCurso);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover usuário-trilha-curso", e);
        }
    }
    
    public UsuarioTrilhaCurso buscarPorId(Integer idUsuarioTrilhaCurso) {
        String sql = "SELECT * FROM TB_SS_USUARIO_TRILHA_CURSO WHERE ID_USUARIO_TRILHA_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuarioTrilhaCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioTrilhaCursoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário-trilha-curso por ID", e);
        }
        
        return null;
    }
    
    public UsuarioTrilhaCurso buscarPorUsuarioETrilhaCurso(Integer idUsuario, Integer idTrilhaCurso) {
        String sql = "SELECT * FROM TB_SS_USUARIO_TRILHA_CURSO WHERE ID_USUARIO = ? AND ID_TRILHA_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            st.setInt(2, idTrilhaCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioTrilhaCursoDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário-trilha-curso por usuário e trilha-curso", e);
        }
        
        return null;
    }
    
    public ArrayList<UsuarioTrilhaCurso> buscarPorUsuario(Integer idUsuario) {
        ArrayList<UsuarioTrilhaCurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_USUARIO_TRILHA_CURSO WHERE ID_USUARIO = ? ORDER BY ID_TRILHA_CURSO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuario);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    lista.add(criarUsuarioTrilhaCursoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário-trilha-curso por usuário", e);
        }
        
        return lista;
    }
    
    public ArrayList<UsuarioTrilhaCurso> buscarPorTrilhaCurso(Integer idTrilhaCurso) {
        ArrayList<UsuarioTrilhaCurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_USUARIO_TRILHA_CURSO WHERE ID_TRILHA_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idTrilhaCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    lista.add(criarUsuarioTrilhaCursoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário-trilha-curso por trilha-curso", e);
        }
        
        return lista;
    }
    
    public ArrayList<UsuarioTrilhaCurso> buscarPorStatusFase(String statusFase) {
        ArrayList<UsuarioTrilhaCurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_USUARIO_TRILHA_CURSO WHERE STATUS_FASE = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, statusFase);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    lista.add(criarUsuarioTrilhaCursoDoResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário-trilha-curso por status", e);
        }
        
        return lista;
    }
    
    public boolean atualizarStatusFase(Integer idUsuarioTrilhaCurso, String statusFase) {
        String sql = "UPDATE TB_SS_USUARIO_TRILHA_CURSO SET STATUS_FASE = ? WHERE ID_USUARIO_TRILHA_CURSO = ?";
        
        if (!idExiste(idUsuarioTrilhaCurso)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, statusFase);
            st.setInt(2, idUsuarioTrilhaCurso);
            
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status da fase", e);
        }
    }
    
    public boolean atualizarStatusFasePorUsuarioETrilhaCurso(Integer idUsuario, Integer idTrilhaCurso, String statusFase) {
        String sql = "UPDATE TB_SS_USUARIO_TRILHA_CURSO SET STATUS_FASE = ? WHERE ID_USUARIO = ? AND ID_TRILHA_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, statusFase);
            st.setInt(2, idUsuario);
            st.setInt(3, idTrilhaCurso);
            
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status da fase por usuário e trilha-curso", e);
        }
    }
    
    public boolean idExiste(Integer idUsuarioTrilhaCurso) {
        String sql = "SELECT 1 FROM TB_SS_USUARIO_TRILHA_CURSO WHERE ID_USUARIO_TRILHA_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idUsuarioTrilhaCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_USUARIO_TRILHA_CURSO", e);
        }
    }
    
    private UsuarioTrilhaCurso criarUsuarioTrilhaCursoDoResultSet(ResultSet rs) throws SQLException {
        UsuarioTrilhaCurso usuarioTrilhaCurso = new UsuarioTrilhaCurso();
        
        usuarioTrilhaCurso.setIdUsuarioTrilhaCurso(rs.getInt("ID_USUARIO_TRILHA_CURSO"));
        usuarioTrilhaCurso.setIdUsuario(rs.getInt("ID_USUARIO"));
        usuarioTrilhaCurso.setIdTrilhaCurso(rs.getInt("ID_TRILHA_CURSO"));
        usuarioTrilhaCurso.setStatusFase(rs.getString("STATUS_FASE"));
        
        if (rs.getObject("DATA_CONCLUSAO") != null) {
            usuarioTrilhaCurso.setDataConclusao(rs.getObject("DATA_CONCLUSAO", LocalDate.class));
        }
        
        return usuarioTrilhaCurso;
    }
}

