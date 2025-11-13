package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.Curso;
import br.com.skill.service.CursoService;
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

@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CursoController {
    
    CursoService cursoService;
    
    @GET
    public Response listarTodos() {
        try {
            List<Curso> cursos = cursoService.listarTodos();
            return Response.ok(cursos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar cursos: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            Curso curso = cursoService.buscarPorId(id);
            return Response.ok(curso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar curso: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/area/{area}")
    public Response buscarPorArea(@PathParam("area") String area) {
        try {
            List<Curso> cursos = cursoService.buscarPorArea(area);
            return Response.ok(cursos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar cursos: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/nivel/{nivel}")
    public Response buscarPorNivel(@PathParam("nivel") String nivel) {
        try {
            List<Curso> cursos = cursoService.buscarPorNivel(nivel);
            return Response.ok(cursos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar cursos: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/buscar")
    public Response buscarPorTitulo(@QueryParam("titulo") String titulo) {
        try {
            List<Curso> cursos = cursoService.buscarPorTitulo(titulo);
            return Response.ok(cursos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar cursos: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(Curso curso) {
        try {
            cursoService.salvar(curso);
            return Response.status(Response.Status.CREATED).entity(curso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar curso: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Curso curso) {
        try {
            curso.setIdCurso(id);
            cursoService.atualizar(curso);
            return Response.ok(curso).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar curso: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            cursoService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar curso: " + e.getMessage()).build();
        }
    }
}
