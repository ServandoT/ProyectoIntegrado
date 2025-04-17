package com.proyectointegrado.skillswap.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "valoraciones")
@Builder
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    public Usuario usuario;

    @NotNull
    @Min(value = 0)
    @Column(columnDefinition = "integer default 0") // TODO No lo hace
    public Long puntuacion;

    @NotBlank
    public String comentario;

    @ManyToOne(cascade = CascadeType.ALL)
    public Clase clase;
}
