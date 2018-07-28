package br.uefs.ecomp.model;

public class Noticia {
    private int id;
    private String titulo;
    private String texto;
    private float nota;
    private int qtdNotas;

    public Noticia(int id, String titulo, String texto, float nota, int qtdNotas) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.nota = nota;
        this.qtdNotas = qtdNotas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getQtdNotas() {
        return qtdNotas;
    }

    public void setQtdNotas(int qtdNotas) {
        this.qtdNotas = qtdNotas;
    }
}