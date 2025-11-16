package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.RespostaUsuario;
import br.com.skill.service.RespostaUsuarioService;
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

@Path("/respostas-usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RespostaUsuarioController {
    
    RespostaUsuarioService respostaUsuarioService = new RespostaUsuarioService();
    
    @GET
    public Response listarTodos() {
        try {
            List<RespostaUsuario> respostasUsuario = respostaUsuarioService.listarTodos();
            return Response.ok(respostasUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar respostas usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            RespostaUsuario respostaUsuario = respostaUsuarioService.buscarPorId(id);
            return Response.ok(respostaUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar resposta usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/prova-usuario/{idProvaUsuario}")
    public Response buscarPorProvaUsuario(@PathParam("idProvaUsuario") Integer idProvaUsuario) {
        try {
            List<RespostaUsuario> respostasUsuario = respostaUsuarioService.buscarPorProvaUsuario(idProvaUsuario);
            return Response.ok(respostasUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar respostas usuário: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/questao/{idQuestao}")
    public Response buscarPorQuestao(@PathParam("idQuestao") Integer idQuestao) {
        try {
            List<RespostaUsuario> respostasUsuario = respostaUsuarioService.buscarPorQuestao(idQuestao);
            return Response.ok(respostasUsuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar respostas usuário: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(RespostaUsuario respostaUsuario) {
        try {
            respostaUsuarioService.salvar(respostaUsuario);
            return Response.status(Response.Status.CREATED).entity(respostaUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar resposta usuário: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, RespostaUsuario respostaUsuario) {
        try {
            respostaUsuario.setIdRespostaUsuario(id);
            respostaUsuarioService.atualizar(respostaUsuario);
            return Response.ok(respostaUsuario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar resposta usuário: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            respostaUsuarioService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar resposta usuário: " + e.getMessage()).build();
        }
    }
}

