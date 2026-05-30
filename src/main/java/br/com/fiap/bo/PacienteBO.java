package br.com.fiap.bo;

import br.com.fiap.dao.PacienteDAO;
import br.com.fiap.entities.Paciente;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.SQLException;
import java.util.List;


@ApplicationScoped
public class PacienteBO {

        private PacienteDAO pacienteDAO;

        public PacienteBO() {
            try {
                pacienteDAO = new PacienteDAO();
            } catch (Exception e) {
                throw new RuntimeException("Erro ao iniciar PacienteDAO", e);
            }
        }

    public void inserir(Paciente paciente) throws SQLException {

        if (paciente.getNome() == null || paciente.getNome().isEmpty()) {
            throw new RuntimeException("Nome do paciente obrigatório.");
        }

        if (paciente.getCpf() == null || paciente.getCpf().length() < 11) {
            throw new RuntimeException("CPF inválido.");
        }

        pacienteDAO.inserir(paciente);
    }


    public List<Paciente> listarTodos() throws SQLException {
        return pacienteDAO.listarTodos();
    }


    public Paciente buscarPorId(Long id) throws SQLException {
        return pacienteDAO.buscarPorId(id);
    }


    public void atualizar(Paciente paciente) throws SQLException {

        if (paciente.getNome() == null || paciente.getNome().isEmpty()) {
            throw new RuntimeException("Nome obrigatório.");
        }

        pacienteDAO.atualizar(paciente);
    }


    public void deletar(Long id) throws SQLException {
        pacienteDAO.deletar(id);
    }
}
