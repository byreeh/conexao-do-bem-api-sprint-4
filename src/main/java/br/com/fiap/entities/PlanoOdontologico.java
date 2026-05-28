package br.com.fiap.entities;

public class PlanoOdontologico {

    private Long id;
    private String nome;
    private double valor;

    public PlanoOdontologico() {
    }

    public PlanoOdontologico(Long id, String nome, double valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "\n\n === Plano Odontologico ===\n" +
                "\nID: " + id +
                "\nNome: " + nome +
                "\nValor: " + valor;
    }
}
