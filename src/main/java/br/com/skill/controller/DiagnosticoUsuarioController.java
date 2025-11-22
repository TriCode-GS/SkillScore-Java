package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.DiagnosticoUsuario;
import br.com.skill.service.DiagnosticoUsuarioService;
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

@Path("/diagnosticos-usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiagnosticoUsuarioController {
    
    DiagnosticoUsuarioService diagnosticoUsuarioService = new DiagnosticoUsuarioService();
    
    @GET
    public Response listarTodos() {
        try {
            List<DiagnosticoUsuario> diagnosticos = diagnosticoUsuarioService.listarTodos();
            return Response.ok(diagnosticos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar diagnósticos usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            DiagnosticoUsuario diagnostico = diagnosticoUsuarioService.buscarPorId(id);
            return Response.ok(diagnostico).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar diagnóstico usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/usuario/{idUsuario}")
    public Response buscarPorUsuario(@PathParam("idUsuario") Integer idUsuario) {
        try {
            List<DiagnosticoUsuario> diagnosticos = diagnosticoUsuarioService.buscarPorUsuario(idUsuario);
            return Response.ok(diagnosticos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar diagnósticos usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/usuario/{idUsuario}/ultimo")
    public Response buscarUltimoPorUsuario(@PathParam("idUsuario") Integer idUsuario) {
        try {
            DiagnosticoUsuario diagnostico = diagnosticoUsuarioService.buscarUltimoPorUsuario(idUsuario);
            return Response.ok(diagnostico).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar último diagnóstico: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/trilha/{idTrilha}")
    public Response buscarPorTrilha(@PathParam("idTrilha") Integer idTrilha) {
        try {
            List<DiagnosticoUsuario> diagnosticos = diagnosticoUsuarioService.buscarPorTrilha(idTrilha);
            return Response.ok(diagnosticos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar diagnósticos usuário: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(DiagnosticoUsuario diagnosticoUsuario) {
        try {
            diagnosticoUsuarioService.salvar(diagnosticoUsuario);
            return Response.status(Response.Status.CREATED).entity(diagnosticoUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar diagnóstico usuário: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, DiagnosticoUsuario diagnosticoUsuario) {
        try {
            diagnosticoUsuario.setIdDiagnostico(id);
            diagnosticoUsuarioService.atualizar(diagnosticoUsuario);
            return Response.ok(diagnosticoUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar diagnóstico usuário: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            diagnosticoUsuarioService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar diagnóstico usuário: " + e.getMessage()).build();
        }
    }
}

