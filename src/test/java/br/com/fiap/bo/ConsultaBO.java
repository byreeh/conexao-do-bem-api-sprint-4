package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.dao.DentistaDAO;
import br.com.fiap.dao.PacienteDAO;
import br.com.fiap.entities.Consulta;
import br.com.fiap.entities.Dentista;
import br.com.fiap.entities.Paciente;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaBO {
    private ConsultaDAO consultaDAO;
    private PacienteDAO pacienteDAO;
    private DentistaDAO dentistaDAO;

    public ConsultaBO() throws SQLException, ClassNotFoundException {
        consultaDAO = new ConsultaDAO();
        pacienteDAO = new PacienteDAO();
        dentistaDAO = new DentistaDAO();
    }

    // ===================== MÉTODO 1 =====================
    // AGENDAR CONSULTA (regra de conflito)

    public void agendar(Consulta consulta) throws SQLException {

        Paciente paciente = pacienteDAO.buscarPorId(consulta.getPacienteId());
        Dentista dentista = dentistaDAO.buscarPorId(consulta.getDentistaId());

        if (paciente == null || !paciente.isAtivo()) {
            throw new RuntimeException("Paciente inválido ou inativo.");
        }

        if (dentista == null || !dentista.isAtivo()) {
            throw new RuntimeException("Dentista inválido ou inativo.");
        }

        List<Consulta> consultas = consultaDAO.listarTodas();

        for (Consulta c : consultas) {
            if (c.getDentistaId().equals(consulta.getDentistaId())) {

                long diff = Math.abs(
                        java.time.Duration.between(c.getDataHora(), consulta.getDataHora()).toMinutes()
                );

                if (diff < 30) {
                    throw new RuntimeException("Conflito de horário para o dentista.");
                }
            }
        }

        if (consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível agendar no passado.");
        }

        consulta.setStatus("AGENDADA");

        consultaDAO.inserir(consulta);
    }

    // =====================MÉTODO2=====================
    // CANCELAR CONSULTA

    public void cancelar(Long id) throws SQLException {

        Consulta consulta = consultaDAO.buscarPorId(id);

        if (consulta == null) {
            throw new RuntimeException("Consulta não encontrada.");
        }

        long horas = java.time.Duration.between(LocalDateTime.now(), consulta.getDataHora()).toHours();

        if (horas < 2) {
            throw new RuntimeException("Cancelamento permitido apenas com 2h de antecedência.");
        }

        consultaDAO.cancelar(id);
    }

    // =====================MÉTODO3=====================
    // RELATÓRIO DO PACIENTE

    public double totalGastoPaciente(Long pacienteId) throws SQLException {

        List<Consulta> consultas = consultaDAO.buscarPorPaciente(pacienteId);

        return consultas.stream()
                .filter(c -> "REALIZADA".equals(c.getStatus()))
                .mapToDouble(Consulta::getValor)
                .sum();
    }

    // ===================== MÉTODO4======================
    // CONFIRMAR CONSULTAS PRÓXIMAS

    public int confirmarProximas() throws SQLException {

        List<Consulta> consultas = consultaDAO.listarTodas();

        int count = 0;

        for (Consulta c : consultas) {

            if ("AGENDADA".equals(c.getStatus())) {

                if (c.getDataHora().isBefore(LocalDateTime.now().plusHours(24))) {
                    consultaDAO.confirmar(c.getId());
                    count++;
                }
            }
        }

        return count;
    }

    // ===================== CRUD BÁSICO =====================

    public List<Consulta> listarTodos() throws SQLException {
        return consultaDAO.listarTodas();
    }

    public Consulta buscarPorId(Long id) throws SQLException {
        return consultaDAO.buscarPorId(id);
    }

    public void atualizar(Consulta consulta) throws SQLException {
        consultaDAO.atualizar(consulta);
    }

    public void deletar(Long id) throws SQLException {
        consultaDAO.deletar(id);
    }
}
