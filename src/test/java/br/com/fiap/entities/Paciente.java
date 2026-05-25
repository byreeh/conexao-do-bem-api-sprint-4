package br.com.fiap.entities;

import java.time.LocalDate;

public class Paciente {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private boolean ativo;
    private String planoOdontologico;

    public Paciente() {
    }

    public Paciente(int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento, boolean ativo, String planoOdontologico) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.ativo = ativo;
        this.planoOdontologico = planoOdontologico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getPlanoOdontologico() {
        return planoOdontologico;
    }

    public void setPlanoOdontologico(String planoOdontologico) {
        this.planoOdontologico = planoOdontologico;
    }

    @Override
    public String toString() {
        return "\n\n=== Paciente - Dados do Paciente ===\n" +
                "id: " + id +
                "\nNome: " + nome +
                "\nCPF: " + cpf +
                "\nTelefone: " + telefone +
                "\nE-mail: " + email +
                "\nData de Nascimento: " + dataNascimento +
                "\nAtivo: " + ativo +
                "\nPlano Odontologico: " + planoOdontologico;
    }
}
