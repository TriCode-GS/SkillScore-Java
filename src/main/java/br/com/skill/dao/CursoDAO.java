package br.com.skill.dao;

import br.com.skill.model.Curso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CursoDAO {
    
    public void adicionar(Curso curso) {
        String sql = "INSERT INTO TB_SS_CURSO (ID_CURSO, TITULO, DESCRICAO, LINK_CURSO, "
                + "AREA_RELACIONADA, NIVEL_RECOMENDADO, DURACAO_HORAS) "
                + "VALUES(SQ_SS_CURSO.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql)) {
            
            comandoDeInsercao.setString(1, curso.getTitulo());
            comandoDeInsercao.setString(2, curso.getDescricao());
            comandoDeInsercao.setString(3, curso.getLinkCurso());
            comandoDeInsercao.setString(4, curso.getAreaRelacionada());
            comandoDeInsercao.setString(5, curso.getNivelRecomendado());
            comandoDeInsercao.setString(6, curso.getDuracaoHoras());
            
            comandoDeInsercao.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public ArrayList<Curso> obterTodosCursos() {
        ArrayList<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_CURSO";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql)) {
            
            ResultSet rs = comandoDeSelecao.executeQuery();
            
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getInt("ID_CURSO"));
                curso.setTitulo(rs.getString("TITULO"));
                curso.setDescricao(rs.getString("DESCRICAO"));
                curso.setLinkCurso(rs.getString("LINK_CURSO"));
                curso.setAreaRelacionada(rs.getString("AREA_RELACIONADA"));
                curso.setNivelRecomendado(rs.getString("NIVEL_RECOMENDADO"));
                curso.setDuracaoHoras(rs.getString("DURACAO_HORAS"));
                cursos.add(curso);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
        return cursos;
    }
    
    public boolean atualizar(Curso curso) {
        String sql = "UPDATE TB_SS_CURSO "
                + "SET TITULO = ?, DESCRICAO = ?, LINK_CURSO = ?, AREA_RELACIONADA = ?, "
                + "NIVEL_RECOMENDADO = ?, DURACAO_HORAS = ? "
                + "WHERE ID_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sql)) {
            
            if (!idExiste(curso.getIdCurso())) {
                return false;
            }
            
            comandoDeAtualizacao.setString(1, curso.getTitulo());
            comandoDeAtualizacao.setString(2, curso.getDescricao());
            comandoDeAtualizacao.setString(3, curso.getLinkCurso());
            comandoDeAtualizacao.setString(4, curso.getAreaRelacionada());
            comandoDeAtualizacao.setString(5, curso.getNivelRecomendado());
            comandoDeAtualizacao.setString(6, curso.getDuracaoHoras());
            comandoDeAtualizacao.setInt(7, curso.getIdCurso());
            
            int linhas = comandoDeAtualizacao.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso", e);
        }
    }
    
    public boolean removerPorId(Integer idCurso) {
        String sql = "DELETE FROM TB_SS_CURSO WHERE ID_CURSO = ?";
        
        if (!idExiste(idCurso)) {
            return false;
        }
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idCurso);
            int linhas = st.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover curso", e);
        }
    }
    
    public Curso buscarPorId(Integer idCurso) {
        String sql = "SELECT * FROM TB_SS_CURSO WHERE ID_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("ID_CURSO"));
                    curso.setTitulo(rs.getString("TITULO"));
                    curso.setDescricao(rs.getString("DESCRICAO"));
                    curso.setLinkCurso(rs.getString("LINK_CURSO"));
                    curso.setAreaRelacionada(rs.getString("AREA_RELACIONADA"));
                    curso.setNivelRecomendado(rs.getString("NIVEL_RECOMENDADO"));
                    curso.setDuracaoHoras(rs.getString("DURACAO_HORAS"));
                    return curso;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso por ID", e);
        }
        
        return null;
    }
    
    public ArrayList<Curso> buscarPorArea(String areaRelacionada) {
        ArrayList<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_CURSO WHERE AREA_RELACIONADA = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, areaRelacionada);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("ID_CURSO"));
                    curso.setTitulo(rs.getString("TITULO"));
                    curso.setDescricao(rs.getString("DESCRICAO"));
                    curso.setLinkCurso(rs.getString("LINK_CURSO"));
                    curso.setAreaRelacionada(rs.getString("AREA_RELACIONADA"));
                    curso.setNivelRecomendado(rs.getString("NIVEL_RECOMENDADO"));
                    curso.setDuracaoHoras(rs.getString("DURACAO_HORAS"));
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cursos por área", e);
        }
        
        return cursos;
    }
    
    public ArrayList<Curso> buscarPorNivel(String nivelRecomendado) {
        ArrayList<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_CURSO WHERE NIVEL_RECOMENDADO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, nivelRecomendado);
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("ID_CURSO"));
                    curso.setTitulo(rs.getString("TITULO"));
                    curso.setDescricao(rs.getString("DESCRICAO"));
                    curso.setLinkCurso(rs.getString("LINK_CURSO"));
                    curso.setAreaRelacionada(rs.getString("AREA_RELACIONADA"));
                    curso.setNivelRecomendado(rs.getString("NIVEL_RECOMENDADO"));
                    curso.setDuracaoHoras(rs.getString("DURACAO_HORAS"));
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cursos por nível", e);
        }
        
        return cursos;
    }
    
    public ArrayList<Curso> buscarPorTitulo(String titulo) {
        ArrayList<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM TB_SS_CURSO WHERE UPPER(TITULO) LIKE UPPER(?)";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setString(1, "%" + titulo + "%");
            
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setIdCurso(rs.getInt("ID_CURSO"));
                    curso.setTitulo(rs.getString("TITULO"));
                    curso.setDescricao(rs.getString("DESCRICAO"));
                    curso.setLinkCurso(rs.getString("LINK_CURSO"));
                    curso.setAreaRelacionada(rs.getString("AREA_RELACIONADA"));
                    curso.setNivelRecomendado(rs.getString("NIVEL_RECOMENDADO"));
                    curso.setDuracaoHoras(rs.getString("DURACAO_HORAS"));
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cursos por título", e);
        }
        
        return cursos;
    }
    
    public boolean idExiste(Integer idCurso) {
        String sql = "SELECT 1 FROM TB_SS_CURSO WHERE ID_CURSO = ?";
        
        try (Connection conexao = new ConnectionFactory().getConnection();
             PreparedStatement st = conexao.prepareStatement(sql)) {
            
            st.setInt(1, idCurso);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ID_CURSO", e);
        }
    }
}
