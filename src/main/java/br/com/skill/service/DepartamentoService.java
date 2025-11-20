package br.com.skill.service;

import java.time.LocalDate;
import java.util.List;

import br.com.skill.dao.DepartamentoDAO;
import br.com.skill.dao.EmpresaDAO;
import br.com.skill.dao.UsuarioDAO;
import br.com.skill.model.Departamento;

public class DepartamentoService {
    
    DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    EmpresaDAO empresaDAO = new EmpresaDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public void salvar(Departamento departamento) {
        validarDepartamento(departamento);
        
        if (departamento.getIdEmpresa() != null) {
            if (empresaDAO.buscarPorId(departamento.getIdEmpresa()) == null) {
                throw new IllegalArgumentException("Empresa não encontrada");
            }
        }
        
        if (departamento.getDataCriacao() == null) {
            departamento.setDataCriacao(LocalDate.now());
        }
        
        departamentoDAO.adicionar(departamento);
    }
    
    public void atualizar(Departamento departamento) {
        validarDepartamento(departamento);
        
        Departamento departamentoExistente = departamentoDAO.buscarPorId(departamento.getIdDepartamento());
        if (departamentoExistente == null) {
            throw new IllegalArgumentException("Departamento não encontrado");
        }
        
        if (departamento.getIdEmpresa() != null) {
            if (empresaDAO.buscarPorId(departamento.getIdEmpresa()) == null) {
                throw new IllegalArgumentException("Empresa não encontrada");
            }
        }
        
        departamentoDAO.atualizar(departamento);
    }
    
    public void deletar(Integer idDepartamento) {
        Departamento departamento = departamentoDAO.buscarPorId(idDepartamento);
        if (departamento == null) {
            throw new IllegalArgumentException("Departamento não encontrado");
        }
        
        if (departamentoDAO.existeUsuarioVinculado(idDepartamento)) {
            throw new IllegalStateException("Não é possível deletar o departamento. Existem usuários vinculados a este departamento.");
        }
        
        departamentoDAO.removerPorId(idDepartamento);
    }
    
    public Departamento buscarPorId(Integer idDepartamento) {
        Departamento departamento = departamentoDAO.buscarPorId(idDepartamento);
        if (departamento == null) {
            throw new IllegalArgumentException("Departamento não encontrado");
        }
        return departamento;
    }
    
    public List<Departamento> listarTodos() {
        return departamentoDAO.obterTodosDepartamentos();
    }
    
    public List<Departamento> buscarPorEmpresa(Integer idEmpresa) {
        return departamentoDAO.buscarPorEmpresa(idEmpresa);
    }
    
    private void validarDepartamento(Departamento departamento) {
        if (departamento == null) {
            throw new IllegalArgumentException("Departamento não pode ser nulo");
        }
        
        if (departamento.getNomeDepartamento() == null || departamento.getNomeDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do departamento é obrigatório");
        }
        
        if (departamento.getNomeDepartamento().length() > 100) {
            throw new IllegalArgumentException("Nome do departamento deve ter no máximo 100 caracteres");
        }
    }
}

