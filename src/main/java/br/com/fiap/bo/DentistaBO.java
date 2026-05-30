package br.com.fiap.bo;

import br.com.fiap.dao.DentistaDAO;
import br.com.fiap.entities.Dentista;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.SQLException;
import java.util.List;



@ApplicationScoped
public class DentistaBO {

    private DentistaDAO dentistaDAO;

    public DentistaBO() {
        try {
            dentistaDAO = new DentistaDAO();
        } catch (Exception e) { // ✅ ADD
            throw new RuntimeException("Erro ao iniciar DentistaDAO", e);
        }
    }


    public void inserir(Dentista dentista) throws SQLException {

        if (dentista.getNome() == null || dentista.getNome().isEmpty()) {
            throw new RuntimeException("Nome do dentista obrigatório.");
        }

        if (dentista.getCro() == null || dentista.getCro().isEmpty()) {
            throw new RuntimeException("CRO obrigatório.");
        }

        dentistaDAO.inserir(dentista);
    }


    public List<Dentista> listarTodos() throws SQLException {
        return dentistaDAO.listarTodos();
    }


    public Dentista buscarPorId(Long id) throws SQLException {
        return dentistaDAO.buscarPorId(id);
    }


    public void atualizar(Dentista dentista) throws SQLException {

        if (dentista.getNome() == null || dentista.getNome().isEmpty()) {
            throw new RuntimeException("Nome obrigatório.");
        }

        dentistaDAO.atualizar(dentista);
    }


    public void deletar(Long id) throws SQLException {
        dentistaDAO.deletar(id);
    }
}
