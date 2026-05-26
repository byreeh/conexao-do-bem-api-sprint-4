package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.dao.DentistaDAO;
import br.com.fiap.dao.PacienteDAO;
import br.com.fiap.entities.Consulta;
import br.com.fiap.entities.Dentista;
import br.com.fiap.entities.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ConsultaBO {

    @Inject
    ConsultaDAO consultaDAO;

    @Inject
    PacienteDAO pacienteDAO;

    @Inject
    DentistaDAO dentistaDAO;

    public void agendar(Consulta consulta) {
        Paciente paciente = pacienteDAO.buscarPorId(consulta.getPacienteId());
        Dentista dentista = dentistaDAO.buscarPorId(consulta.getDentistaId());

        if (paciente == null || !paciente.isAtivo())
            throw new RuntimeException("Paciente inválido ou inativo.");

        if (dentista == null || !dentista.isAtivo())
            throw new RuntimeException("Dentista inválido ou inativo.");

        List<Consulta> consultas = consultaDAO.listarTodas();
        for (Consulta c : consultas) {
            if (c.getDentistaId().equals(consulta.getDentistaId())) {
                long diff = Math.abs(
                        java.time.Duration.between(c.getDataHora(), consulta.getDataHora()).toMinutes()
                );
                if (diff < 30)
                    throw new RuntimeException("Conflito de horário para o dentista.");
            }
        }

        if (consulta.getDataHora().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Não é possível agendar no passado.");

        consulta.setStatus(Consulta.Status.AGENDADA);
        consultaDAO.inserir(consulta);
    }

    public void cancelar(Long id) {
        Consulta consulta = consultaDAO.buscarPorId(id);
        long horas = java.time.Duration.between(LocalDateTime.now(), consulta.getDataHora()).toHours();
        if (horas < 2)
            throw new RuntimeException("Cancelamento permitido apenas com 2h de antecedência.");
        consultaDAO.atualizarStatus(id, Consulta.Status.CANCELADA);
    }

    public double totalGastoPaciente(Long pacienteId) {
        return consultaDAO.buscarPorPaciente(pacienteId).stream()
                .filter(c -> Consulta.Status.REALIZADA.equals(c.getStatus()))
                .mapToDouble(Consulta::getValor)
                .sum();
    }

    public int confirmarProximas() {
        List<Consulta> consultas = consultaDAO.listarTodas();
        int count = 0;
        for (Consulta c : consultas) {
            if (Consulta.Status.AGENDADA.equals(c.getStatus()) &&
                    c.getDataHora().isBefore(LocalDateTime.now().plusHours(24))) {
                consultaDAO.atualizarStatus(c.getId(), Consulta.Status.CONFIRMADA);
                count++;
            }
        }
        return count;
    }

    public List<Consulta> listarTodos() { return consultaDAO.listarTodas(); }
    public Consulta buscarPorId(Long id) { return consultaDAO.buscarPorId(id); }
    public void atualizar(Consulta consulta) { consultaDAO.atualizar(consulta); }
    public void deletar(Long id) { consultaDAO.deletar(id); }
}