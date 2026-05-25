package br.com.fiap.resource;

import br.com.fiap.bo.PacienteBO;
import br.com.fiap.entities.Paciente;

import java.sql.SQLException;
import java.util.List;

public class DentistaResource {

    private PacienteBO pacienteBO;

    public PacienteResource() throws SQLException, ClassNotFoundException {
        pacienteBO = new PacienteBO();
    }


    @POST
    public String inserir(Paciente paciente) throws SQLException {

        pacienteBO.inserir(paciente);

        return "Paciente cadastrado com sucesso!";
    }


    @GET
    public List<Paciente> listarTodos() throws SQLException {
        return pacienteBO.listarTodos();
    }


    @GET
    @Path("/{id}")

    public Paciente buscarPorId(@PathParam("id") Long id)
            throws SQLException {

        return pacienteBO.buscarPorId(id);
    }


    @PUT

    public String atualizar(Paciente paciente)
            throws SQLException {

        pacienteBO.atualizar(paciente);

        return "Paciente atualizado com sucesso!";
    }


    @DELETE
    @Path("/{id}")

    public String deletar(@PathParam("id") Long id)
            throws SQLException {

        pacienteBO.deletar(id);

        return "Paciente deletado com sucesso!";
    }
}
