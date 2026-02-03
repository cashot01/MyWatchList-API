package com.project.mywatchlist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String tipo; // FILME ou SERIE

    private Integer nota; // 1 a 5

    private String review;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusFilme status;


    public Filme() {
    }

    public Filme(Long id, String titulo, String tipo, Integer nota, String review, StatusFilme status) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.nota = nota;
        this.review = review;
        this.status = status;
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

    public StatusFilme getStatus() {
        return status;
    }

    public void setStatus(StatusFilme status) {
        this.status = status;
    }
}
