package com.project.mywatchlist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "filmes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "titulo do filme não pode estar vazio")
    @Column(name = "titulo")
    private String titulo;

    @Column(name = "tipo")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo; // filme ou serie

    @Column(name = "genero")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @NotNull(message = "nota não pode ser nula")
    @Min(1)
    @Max(10)
    @Column(name = "nota")
    private Integer nota; // 1 a 10

    @NotBlank(message = "review não pode ser nula")
    @Column(name = "review")
    private String review;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private StatusFilme status;

}
