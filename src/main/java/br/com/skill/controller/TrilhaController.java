package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.Trilha;
import br.com.skill.service.TrilhaService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/trilhas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrilhaController {
    
    TrilhaService trilhaService = new TrilhaService();
    
    @GET
    public Response listarTodos() {
        try {
            List<Trilha> trilhas = trilhaService.listarTodos();
            return Response.ok(trilhas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar trilhas: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            Trilha trilha = trilhaService.buscarPorId(id);
            return Response.ok(trilha).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trilha: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/status/{status}")
    public Response buscarPorStatus(@PathParam("status") String status) {
        try {
            List<Trilha> trilhas = trilhaService.buscarPorStatus(status);
            return Response.ok(trilhas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trilhas: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/buscar")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        try {
            List<Trilha> trilhas = trilhaService.buscarPorNome(nome);
            return Response.ok(trilhas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trilhas: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(Trilha trilha) {
        try {
            trilhaService.salvar(trilha);
            return Response.status(Response.Status.CREATED).entity(trilha).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar trilha: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Trilha trilha) {
        try {
            trilha.setIdTrilha(id);
            trilhaService.atualizar(trilha);
            return Response.ok(trilha).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar trilha: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            trilhaService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar trilha: " + e.getMessage()).build();
        }
    }
}
