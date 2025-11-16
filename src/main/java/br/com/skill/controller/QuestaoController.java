package br.com.skill.controller;

import java.util.List;

import br.com.skill.model.Questao;
import br.com.skill.service.QuestaoService;
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

@Path("/questoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestaoController {
    
    QuestaoService questaoService;
    
    @GET
    public Response listarTodos() {
        try {
            List<Questao> questoes = questaoService.listarTodos();
            return Response.ok(questoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar questões: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        try {
            Questao questao = questaoService.buscarPorId(id);
            return Response.ok(questao).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar questão: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/prova/{idProva}")
    public Response buscarPorProva(@PathParam("idProva") Integer idProva) {
        try {
            List<Questao> questoes = questaoService.buscarPorProva(idProva);
            return Response.ok(questoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar questões: " + e.getMessage()).build();
        }
    }
    
    @POST
    public Response salvar(Questao questao) {
        try {
            questaoService.salvar(questao);
            return Response.status(Response.Status.CREATED).entity(questao).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao salvar questão: " + e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, Questao questao) {
        try {
            questao.setIdQuestao(id);
            questaoService.atualizar(questao);
            return Response.ok(questao).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar questão: " + e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) {
        try {
            questaoService.deletar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar questão: " + e.getMessage()).build();
        }
    }
}

