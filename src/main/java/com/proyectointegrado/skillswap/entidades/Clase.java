package com.proyectointegrado.skillswap.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "clases_categorias",
            joinColumns = @JoinColumn(name = "clase_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;


    //    TODO revisar
    @NotNull
    @Pattern(regexp = "^\\d{1,2}:\\d{2}$", message = "El formato debe ser HH:mm")
    private String duracion;

    @NotNull
    @Min(value = 0)
//    @Column(columnDefinition = "integer default 0") // TODO No lo hace
    private Long precio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    @JsonManagedReference
    private Usuario profesor;

    private String idioma; // TODO Mirar si se puede hacer un enum

    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Valoracion> valoraciones;
}
