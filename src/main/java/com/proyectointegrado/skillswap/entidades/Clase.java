package com.proyectointegrado.skillswap.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "clases")
@Builder
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    public String titulo;

    @NotNull
    @OneToOne
    public Categoria categoria;

    @NotNull
    @Min(value = 0)
//    @Column(columnDefinition = "integer default 0") // TODO No lo hace
    public Long precio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    @JsonManagedReference
    public Usuario profesor;

    public String idioma; // TODO Mirar si se puede hacer un enum

    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Valoracion> valoraciones;
}
