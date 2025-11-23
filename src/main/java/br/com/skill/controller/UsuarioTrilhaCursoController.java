package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.UsuarioTrilhaCurso;
import br.com.skill.service.UsuarioTrilhaCursoService;
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

@Path("/usuario-trilha-cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioTrilhaCursoController {
    
    UsuarioTrilhaCursoService usuarioTrilhaCursoService = new UsuarioTrilhaCursoService();
    
    @GET
    public Response listarTodos() {
        try {
            List<UsuarioTrilhaCurso> lista = usuarioTrilhaCursoService.listarTodos();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar usuário-trilha-cursos: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            UsuarioTrilhaCurso usuarioTrilhaCurso = usuarioTrilhaCursoService.buscarPorId(id);
            return Response.ok(usuarioTrilhaCurso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar usuário-trilha-curso: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/usuario/{idUsuario}")
    public Response buscarPorUsuario(@PathParam("idUsuario") Integer idUsuario) {
        try {
            List<UsuarioTrilhaCurso> lista = usuarioTrilhaCursoService.buscarPorUsuario(idUsuario);
            return Response.ok(lista).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar usuário-trilha-cursos por usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/trilha-curso/{idTrilhaCurso}")
    public Response buscarPorTrilhaCurso(@PathParam("idTrilhaCurso") Integer idTrilhaCurso) {
        try {
            List<UsuarioTrilhaCurso> lista = usuarioTrilhaCursoService.buscarPorTrilhaCurso(idTrilhaCurso);
            return Response.ok(lista).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar usuário-trilha-cursos por trilha-curso: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/status/{statusFase}")
    public Response buscarPorStatusFase(@PathParam("statusFase") String statusFase) {
        try {
            List<UsuarioTrilhaCurso> lista = usuarioTrilhaCursoService.buscarPorStatusFase(statusFase);
            return Response.ok(lista).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar usuário-trilha-cursos por status: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(UsuarioTrilhaCurso usuarioTrilhaCurso) {
        try {
            usuarioTrilhaCursoService.salvar(usuarioTrilhaCurso);
            return Response.status(Response.Status.CREATED).entity(usuarioTrilhaCurso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar usuário-trilha-curso: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, UsuarioTrilhaCurso usuarioTrilhaCurso) {
        try {
            usuarioTrilhaCurso.setIdUsuarioTrilhaCurso(id);
            usuarioTrilhaCursoService.atualizar(usuarioTrilhaCurso);
            return Response.ok(usuarioTrilhaCurso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar usuário-trilha-curso: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/usuario/{idUsuario}/trilha-curso/{idTrilhaCurso}/status-fase")
    public Response atualizarStatusFase(@PathParam("idUsuario") Integer idUsuario, 
                                         @PathParam("idTrilhaCurso") Integer idTrilhaCurso, 
                                         UsuarioTrilhaCurso usuarioTrilhaCurso) {
        try {
            String statusFase = usuarioTrilhaCurso.getStatusFase();
            
            if (statusFase == null || statusFase.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Status da fase é obrigatório").build();
            }
            
            usuarioTrilhaCursoService.atualizarStatusFase(idUsuario, idTrilhaCurso, statusFase);
            
            UsuarioTrilhaCurso atualizado = usuarioTrilhaCursoService.buscarPorUsuario(idUsuario).stream()
                    .filter(utc -> utc.getIdTrilhaCurso().equals(idTrilhaCurso))
                    .findFirst()
                    .orElse(null);
            
            if (atualizado == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Registro não encontrado após atualização").build();
            }
            
            return Response.ok(atualizado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar status da fase: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            usuarioTrilhaCursoService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar usuário-trilha-curso: " + e.getMessage()).build();
        }
    }
}


