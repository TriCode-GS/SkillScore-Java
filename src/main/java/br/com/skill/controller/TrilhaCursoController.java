package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.TrilhaCurso;
import br.com.skill.model.TrilhaCursoCompleto;
import br.com.skill.service.TrilhaCursoService;
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

@Path("/trilha-cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrilhaCursoController {
    
    TrilhaCursoService trilhaCursoService = new TrilhaCursoService();
    
    @GET
    public Response listarTodos() {
        try {
            List<TrilhaCurso> trilhaCursos = trilhaCursoService.listarTodos();
            return Response.ok(trilhaCursos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar trilha-cursos: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            TrilhaCurso trilhaCurso = trilhaCursoService.buscarPorId(id);
            return Response.ok(trilhaCurso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trilha-curso: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/trilha/{idTrilha}")
    public Response buscarPorTrilha(@PathParam("idTrilha") Integer idTrilha) {
        try {
            List<TrilhaCursoCompleto> trilhaCursos = trilhaCursoService.buscarPorTrilhaComDadosCurso(idTrilha);
            return Response.ok(trilhaCursos).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trilha-cursos: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/curso/{idCurso}")
    public Response buscarPorCurso(@PathParam("idCurso") Integer idCurso) {
        try {
            List<TrilhaCurso> trilhaCursos = trilhaCursoService.buscarPorCurso(idCurso);
            return Response.ok(trilhaCursos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trilha-cursos: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/status/{statusFase}")
    public Response buscarPorStatusFase(@PathParam("statusFase") String statusFase) {
        try {
            List<TrilhaCurso> trilhaCursos = trilhaCursoService.buscarPorStatusFase(statusFase);
            return Response.ok(trilhaCursos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trilha-cursos: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(TrilhaCurso trilhaCurso) {
        try {
            trilhaCursoService.salvar(trilhaCurso);
            return Response.status(Response.Status.CREATED).entity(trilhaCurso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar trilha-curso: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, TrilhaCurso trilhaCurso) {
        try {
            trilhaCurso.setIdTrilhaCurso(id);
            trilhaCursoService.atualizar(trilhaCurso);
            return Response.ok(trilhaCurso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar trilha-curso: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}/status-fase")
    public Response atualizarStatusFase(@PathParam("id") Integer id, TrilhaCurso trilhaCurso) {
        try {
            String statusFase = trilhaCurso.getStatusFase();
            
            if (statusFase == null || statusFase.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Status da fase é obrigatório").build();
            }
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Este endpoint requer o ID do usuário. Use: PUT /trilha-cursos/{id}/status-fase/{idUsuario}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar status da fase: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}/status-fase/{idUsuario}")
    public Response atualizarStatusFaseComUsuario(@PathParam("id") Integer id, 
                                                   @PathParam("idUsuario") Integer idUsuario, 
                                                   TrilhaCurso trilhaCurso) {
        try {
            String statusFase = trilhaCurso.getStatusFase();
            
            if (statusFase == null || statusFase.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Status da fase é obrigatório").build();
            }
            
            trilhaCursoService.atualizarStatusFase(id, statusFase, idUsuario);
            
            TrilhaCurso trilhaCursoAtualizado = trilhaCursoService.buscarPorId(id);
            return Response.ok(trilhaCursoAtualizado).build();
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
            trilhaCursoService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar trilha-curso: " + e.getMessage()).build();
        }
    }
}
