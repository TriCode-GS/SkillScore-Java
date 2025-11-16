package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.ProvaUsuario;
import br.com.skill.service.ProvaUsuarioService;
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

@Path("/provas-usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProvaUsuarioController {
    
    ProvaUsuarioService provaUsuarioService = new ProvaUsuarioService();
    
    @GET
    public Response listarTodos() {
        try {
            List<ProvaUsuario> provasUsuario = provaUsuarioService.listarTodos();
            return Response.ok(provasUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar provas usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            ProvaUsuario provaUsuario = provaUsuarioService.buscarPorId(id);
            return Response.ok(provaUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar prova usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/prova/{idProva}")
    public Response buscarPorProva(@PathParam("idProva") Integer idProva) {
        try {
            List<ProvaUsuario> provasUsuario = provaUsuarioService.buscarPorProva(idProva);
            return Response.ok(provasUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar provas usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/usuario/{idUsuario}")
    public Response buscarPorUsuario(@PathParam("idUsuario") Integer idUsuario) {
        try {
            List<ProvaUsuario> provasUsuario = provaUsuarioService.buscarPorUsuario(idUsuario);
            return Response.ok(provasUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar provas usuário: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(ProvaUsuario provaUsuario) {
        try {
            provaUsuarioService.salvar(provaUsuario);
            return Response.status(Response.Status.CREATED).entity(provaUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar prova usuário: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, ProvaUsuario provaUsuario) {
        try {
            provaUsuario.setIdProvaUsuario(id);
            provaUsuarioService.atualizar(provaUsuario);
            return Response.ok(provaUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar prova usuário: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            provaUsuarioService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar prova usuário: " + e.getMessage()).build();
        }
    }
}

