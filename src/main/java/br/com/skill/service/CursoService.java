package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.CursoDAO;
import br.com.skill.model.Curso;

public class CursoService {
    
    CursoDAO cursoDAO = new CursoDAO();
    
    public void salvar(Curso curso) {
        validarCurso(curso);
        cursoDAO.adicionar(curso);
    }
    
    public void atualizar(Curso curso) {
        validarCurso(curso);
        
        Curso cursoExistente = cursoDAO.buscarPorId(curso.getIdCurso());
        if (cursoExistente == null) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        
        cursoDAO.atualizar(curso);
    }
    
    public void deletar(Integer idCurso) {
        Curso curso = cursoDAO.buscarPorId(idCurso);
        if (curso == null) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        
        cursoDAO.removerPorId(idCurso);
    }
    
    public Curso buscarPorId(Integer idCurso) {
        Curso curso = cursoDAO.buscarPorId(idCurso);
        if (curso == null) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        return curso;
    }
    
    public List<Curso> listarTodos() {
        return cursoDAO.obterTodosCursos();
    }
    
    public List<Curso> buscarPorArea(String areaRelacionada) {
        return cursoDAO.buscarPorArea(areaRelacionada);
    }
    
    public List<Curso> buscarPorNivel(String nivelRecomendado) {
        return cursoDAO.buscarPorNivel(nivelRecomendado);
    }
    
    public List<Curso> buscarPorTitulo(String titulo) {
        return cursoDAO.buscarPorTitulo(titulo);
    }
    
    private void validarCurso(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("Curso não pode ser nulo");
        }
        
        if (curso.getTitulo() == null || curso.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do curso é obrigatório");
        }
        
        if (curso.getTitulo().length() > 100) {
            throw new IllegalArgumentException("Título do curso deve ter no máximo 100 caracteres");
        }
        
        if (curso.getDescricao() != null && curso.getDescricao().length() > 600) {
            throw new IllegalArgumentException("Descrição do curso deve ter no máximo 600 caracteres");
        }
    }
}
