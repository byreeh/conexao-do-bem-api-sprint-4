package br.com.fiap.resource;

import br.com.fiap.bo.DentistaBO;
import br.com.fiap.entities.Dentista;
import jakarta.ws.rs.*;

import java.sql.SQLException;
import java.util.List;

public class ConsultaResource {

    private DentistaBO dentistaBO;

    public void DentistaResource() throws SQLException, ClassNotFoundException {
        dentistaBO = new DentistaBO();
    }


    @POST
    public String inserir(Dentista dentista) throws SQLException {

        dentistaBO.inserir(dentista);

        return "Dentista cadastrado com sucesso!";
    }


    @GET
    public List<Dentista> listarTodos() throws SQLException {

        return dentistaBO.listarTodos();
    }


    @GET
    @Path("/{id}")

    public Dentista buscarPorId(@PathParam("id") Long id)
            throws SQLException {

        return dentistaBO.buscarPorId(id);
    }


    @PUT
    public String atualizar(Dentista dentista)
            throws SQLException {

        dentistaBO.atualizar(dentista);

        return "Dentista atualizado com sucesso!";
    }


    @DELETE
    @Path("/{id}")

    public String deletar(@PathParam("id") Long id)
            throws SQLException {

        dentistaBO.deletar(id);

        return "Dentista deletado com sucesso!";
    }
}
