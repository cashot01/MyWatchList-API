package com.project.mywatchlist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String tipo; // FILME ou SERIE

    private boolean assistido;

    private Integer nota; // 1 a 5

    private String review;

    private boolean naWatchlist;

    public Filme() {
    }

    public Filme(Long id, String titulo, String tipo, boolean assistido, Integer nota, String review, boolean naWatchlist) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.assistido = assistido;
        this.nota = nota;
        this.review = review;
        this.naWatchlist = naWatchlist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isNaWatchlist() {
        return naWatchlist;
    }

    public void setNaWatchlist(boolean naWatchlist) {
        this.naWatchlist = naWatchlist;
    }
}
