package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.Login;
import br.com.skill.service.LoginService;
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

@Path("/logins")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginController {
    
    LoginService loginService = new LoginService();
    
    @GET
    public Response listarTodos() {
        try {
            List<Login> logins = loginService.listarTodos();
            return Response.ok(logins).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar logins: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            Login login = loginService.buscarPorId(id);
            return Response.ok(login).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar login: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/usuario/{idUsuario}")
    public Response buscarPorUsuario(@PathParam("idUsuario") Integer idUsuario) {
        try {
            List<Login> logins = loginService.buscarPorUsuario(idUsuario);
            return Response.ok(logins).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar logins: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(Login login) {
        try {
            loginService.salvar(login);
            return Response.status(Response.Status.CREATED).entity(login).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar login: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Login login) {
        try {
            login.setIdLogin(id);
            loginService.atualizar(login);
            return Response.ok(login).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar login: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            loginService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar login: " + e.getMessage()).build();
        }
    }
    
    @POST
    @Path("/autenticar/administrador")
    public Response autenticarAdministrador(@QueryParam("email") String email, @QueryParam("senha") String senha) {
        try {
            Login login = loginService.autenticarAdministrador(email, senha);
            return Response.ok(login).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao autenticar administrador: " + e.getMessage()).build();
        }
    }
    
    @POST
    @Path("/autenticar/usuario")
    public Response autenticarUsuario(@QueryParam("email") String email, @QueryParam("senha") String senha) {
        try {
            Login login = loginService.autenticarUsuario(email, senha);
            return Response.ok(login).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao autenticar usuário: " + e.getMessage()).build();
        }
    }
    
    @POST
    @Path("/autenticar/administrador-emp")
    public Response autenticarAdministradorEmp(@QueryParam("email") String email, @QueryParam("senha") String senha) {
        try {
            Login login = loginService.autenticarAdministradorEmp(email, senha);
            return Response.ok(login).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao autenticar administrador de empresa: " + e.getMessage()).build();
        }
    }
    
    @POST
    @Path("/autenticar/gestor")
    public Response autenticarGestor(@QueryParam("email") String email, @QueryParam("senha") String senha) {
        try {
            Login login = loginService.autenticarGestor(email, senha);
            return Response.ok(login).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao autenticar gestor: " + e.getMessage()).build();
        }
    }
}
