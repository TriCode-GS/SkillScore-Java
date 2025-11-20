package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.Departamento;
import br.com.skill.service.DepartamentoService;
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

@Path("/departamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartamentoController {
    
    DepartamentoService departamentoService = new DepartamentoService();
    
    @GET
    public Response listarTodos() {
        try {
            List<Departamento> departamentos = departamentoService.listarTodos();
            return Response.ok(departamentos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar departamentos: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            Departamento departamento = departamentoService.buscarPorId(id);
            return Response.ok(departamento).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar departamento: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/empresa/{idEmpresa}")
    public Response buscarPorEmpresa(@PathParam("idEmpresa") Integer idEmpresa) {
        try {
            List<Departamento> departamentos = departamentoService.buscarPorEmpresa(idEmpresa);
            return Response.ok(departamentos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar departamentos: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(Departamento departamento) {
        try {
            departamentoService.salvar(departamento);
            return Response.status(Response.Status.CREATED).entity(departamento).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar departamento: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Departamento departamento) {
        try {
            departamento.setIdDepartamento(id);
            departamentoService.atualizar(departamento);
            return Response.ok(departamento).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar departamento: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            departamentoService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar departamento: " + e.getMessage()).build();
        }
    }
}

