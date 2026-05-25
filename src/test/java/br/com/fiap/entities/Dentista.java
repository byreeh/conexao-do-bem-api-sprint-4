package br.com.fiap.entities;

public class Dentista {
    private int id;
    private String nome;
    private String cro;
    private String especialidade;
    private boolean ativo;

    public Dentista(){
    }

    public Dentista(int id, String nome, String cro, String especialidade, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cro = cro;
        this.especialidade = especialidade;
        this.ativo = ativo;
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

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "\n\n=== Dentista ===\n" +
                "\nID: " + id +
                "\nNome: " + nome +
                "\nCRO: " + cro +
                "\nEspecialidade: " + especialidade +
                "\nAtivo: " + ativo;
    }
}
