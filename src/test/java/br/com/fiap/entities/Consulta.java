package br.com.fiap.entities;

import java.time.LocalDateTime;

public class Consulta {

    private int id;
    private int pacienteId;
    private int dentistaId;
    private LocalDateTime dataHora;
    private double valor;
    private Status status;

    public enum Status{
        AGENDADA,
        CONFIRMADA,
        CANCELADA,
        REALIZADA
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpacienteId() {
        return pacienteId;
    }

    public void setpacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getDentistaId() {
        return dentistaId;
    }

    public void setDentistaId(int dentistaId) {
        this.dentistaId = dentistaId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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
                "\nPaicente ID: " + pacienteId +
                "\nDentista ID: " + dentistaId +
                "\nData e Hora=" + dataHora +
                "\nValor=" + valor +
                "\nStatus=" + status;
    }
}
