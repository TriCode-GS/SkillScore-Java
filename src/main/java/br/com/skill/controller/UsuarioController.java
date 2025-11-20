package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.Usuario;
import br.com.skill.service.UsuarioService;
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

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {
    
    UsuarioService usuarioService = new UsuarioService();
    
    @GET
    public Response listarTodos() {
        try {
            List<Usuario> usuarios = usuarioService.listarTodos();
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar usuários: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return Response.ok(usuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/empresa/{idEmpresa}")
    public Response buscarPorEmpresa(@PathParam("idEmpresa") Integer idEmpresa) {
        try {
            List<Usuario> usuarios = usuarioService.buscarPorEmpresa(idEmpresa);
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar usuários: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/departamento/{idDepartamento}")
    public Response buscarPorDepartamento(@PathParam("idDepartamento") Integer idDepartamento) {
        try {
            List<Usuario> usuarios = usuarioService.buscarPorDepartamento(idDepartamento);
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar usuários: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/administradores-emp")
    public Response listarAdministradoresEmp() {
        try {
            List<Usuario> usuarios = usuarioService.buscarAdministradoresEmp();
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar administradores da empresa: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/gestores")
    public Response listarGestores() {
        try {
            List<Usuario> usuarios = usuarioService.buscarGestores();
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar gestores: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(Usuario usuario) {
        try {
            usuarioService.salvar(usuario);
            return Response.status(Response.Status.CREATED).entity(usuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar usuário: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Usuario usuario) {
        try {
            usuario.setIdUsuario(id);
            usuarioService.atualizar(usuario);
            return Response.ok(usuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar usuário: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}/empresa")
    public Response vincularEmpresa(@PathParam("id") Integer idUsuario, Usuario usuario) {
        try {
            Integer idEmpresa = usuario.getIdEmpresa();
            usuarioService.vincularEmpresa(idUsuario, idEmpresa);
            
            Usuario usuarioAtualizado = usuarioService.buscarPorId(idUsuario);
            return Response.ok(usuarioAtualizado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao vincular usuário à empresa: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}/departamento")
    public Response vincularGestorADepartamento(@PathParam("id") Integer idUsuario, Usuario usuario) {
        try {
            Integer idDepartamento = usuario.getIdDepartamento();
            usuarioService.vincularGestorADepartamento(idUsuario, idDepartamento);
            
            Usuario usuarioAtualizado = usuarioService.buscarPorId(idUsuario);
            return Response.ok(usuarioAtualizado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao vincular gestor ao departamento: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            usuarioService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar usuário: " + e.getMessage()).build();
        }
    }
}
