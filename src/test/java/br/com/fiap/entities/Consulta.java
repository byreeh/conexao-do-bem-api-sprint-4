package br.com.fiap.entities;

import java.time.Instant;
import java.time.LocalDateTime;

public class Consulta {

    private Long id;
    private Long pacienteId;
    private Long dentistaId;
    private LocalDateTime dataHora;
    private double valor;
    private Status status;


    public enum Status{
        AGENDADA,
        CONFIRMADA,
        CANCELADA,
        REALIZADA
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setDentistaId(Long dentistaId) {
        this.dentistaId = dentistaId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\n\n === Consulta ===\n" +
                "\nID: " +
                "\nPaciente ID: " + pacienteId +
                "\nDentista ID: " + dentistaId +
                "\nData e Hora=" + dataHora +
                "\nValor=" + valor +
                "\nStatus=" + status;
    }
}
