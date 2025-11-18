package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.Empresa;
import br.com.skill.service.EmpresaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/empresas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpresaController {
    
    EmpresaService empresaService = new EmpresaService();
    
    @GET
    public Response listarTodos() {
        try {
            List<Empresa> empresas = empresaService.listarTodos();
            return Response.ok(empresas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar empresas: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            Empresa empresa = empresaService.buscarPorId(id);
            return Response.ok(empresa).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar empresa: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/cnpj/{cnpj}")
    public Response buscarPorCnpj(@PathParam("cnpj") String cnpj) {
        try {
            Empresa empresa = empresaService.buscarPorCnpj(cnpj);
            if (empresa == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Empresa não encontrada").build();
            }
            return Response.ok(empresa).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar empresa: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/com-administrador")
    public Response listarComAdministrador() {
        try {
            List<Empresa> empresas = empresaService.buscarComAdministrador();
            return Response.ok(empresas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar empresas com administrador: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/administrador/{idAdministrador}")
    public Response buscarPorAdministrador(@PathParam("idAdministrador") Integer idAdministrador) {
        try {
            List<Empresa> empresas = empresaService.buscarPorAdministrador(idAdministrador);
            return Response.ok(empresas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar empresas por administrador: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(Empresa empresa) {
        try {
            empresaService.salvar(empresa);
            return Response.status(Response.Status.CREATED).entity(empresa).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar empresa: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Empresa empresa) {
        try {
            empresa.setIdEmpresa(id);
            empresaService.atualizar(empresa);
            return Response.ok(empresa).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar empresa: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            empresaService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar empresa: " + e.getMessage()).build();
        }
    }
}
