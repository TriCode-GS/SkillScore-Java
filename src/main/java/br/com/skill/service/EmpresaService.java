package br.com.skill.service;

import java.util.List;

import br.com.skill.dao.EmpresaDAO;
import br.com.skill.model.Empresa;

public class EmpresaService {
    
    EmpresaDAO empresaDAO = new EmpresaDAO();
    
    public void salvar(Empresa empresa) {
        validarEmpresa(empresa);
        
        Empresa empresaExistente = empresaDAO.buscarPorCnpj(empresa.getCnpj());
        if (empresaExistente != null && !empresaExistente.getIdEmpresa().equals(empresa.getIdEmpresa())) {
            throw new IllegalArgumentException("CNPJ já cadastrado no sistema");
        }
        
        empresaDAO.adicionar(empresa);
    }
    
    public void atualizar(Empresa empresa) {
        validarEmpresa(empresa);
        
        Empresa empresaExistente = empresaDAO.buscarPorId(empresa.getIdEmpresa());
        if (empresaExistente == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        
        Empresa empresaComCnpj = empresaDAO.buscarPorCnpj(empresa.getCnpj());
        if (empresaComCnpj != null && !empresaComCnpj.getIdEmpresa().equals(empresa.getIdEmpresa())) {
            throw new IllegalArgumentException("CNPJ já cadastrado em outra empresa");
        }
        
        empresaDAO.atualizar(empresa);
    }
    
    public void deletar(Integer idEmpresa) {
        Empresa empresa = empresaDAO.buscarPorId(idEmpresa);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        
        empresaDAO.removerPorId(idEmpresa);
    }
    
    public Empresa buscarPorId(Integer idEmpresa) {
        Empresa empresa = empresaDAO.buscarPorId(idEmpresa);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }
        return empresa;
    }
    
    public List<Empresa> listarTodos() {
        return empresaDAO.obterTodasEmpresas();
    }
    
    public Empresa buscarPorCnpj(String cnpj) {
        return empresaDAO.buscarPorCnpj(cnpj);
    }
    
    private void validarEmpresa(Empresa empresa) {
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não pode ser nula");
        }
        
        if (empresa.getNomeEmpresa() == null || empresa.getNomeEmpresa().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da empresa é obrigatório");
        }
        
        if (empresa.getCnpj() == null || empresa.getCnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório");
        }
        
        if (empresa.getCnpj().length() < 14 || empresa.getCnpj().length() > 18) {
            throw new IllegalArgumentException("CNPJ deve ter entre 14 e 18 caracteres");
        }
    }
}
