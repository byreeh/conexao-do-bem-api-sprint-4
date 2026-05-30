package br.com.fiap.resource;

import br.com.fiap.bo.PacienteBO;
import br.com.fiap.entities.Paciente;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.sql.SQLException;


@Path("/paciente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject PacienteBO pacienteBO;

    @POST
    public Response inserir(Paciente paciente)  {

        try {

            pacienteBO.inserir(paciente);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensagem\": \"Paciente cadastrado com sucesso\"}")
                    .build();

        } catch (SQLException e) {

            return Response.status(500)
                    .entity("Erro ao cadastrar paciente")
                    .build();
        }
    }


    @GET
    public Response listarTodos() {

        try {

            List<Paciente> lista = pacienteBO.listarTodos();
            return Response.ok(lista).build();

        } catch (SQLException e) {

            return Response.status(500)
                    .entity("Erro ao listar pacientes")
                    .build();
        }
    }


    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {

        try {
            Paciente paciente = pacienteBO.buscarPorId(id);
            return Response.ok(paciente).build();

        } catch (SQLException e) {

            return Response.status(500)
                    .entity("Erro ao buscar paciente")
                    .build();
        }

    }


    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Paciente paciente) {

        try {

            paciente.setId(id);
            pacienteBO.atualizar(paciente);
            return Response.ok("Paciente atualizado com sucesso!").build();

        } catch (SQLException e) {

            return Response.status(500)
                    .entity("Erro ao atualizar paciente")
                    .build();
        }

    }


    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {

        try {

            pacienteBO.deletar(id);
            return Response.ok("Paciente deletado com sucesso").build();

        } catch (SQLException e) {

            return Response.status(500)
                    .entity("Erro ao deletar paciente")
                    .build();
        }
    }

}
