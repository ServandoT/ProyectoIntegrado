package com.proyectointegrado.skillswap.DTOs;

import lombok.Data;

@Data
public class UsuarioModificar {
//    TODO validar cada campo con las etiquetas @NotNull, @NotBlank, etc.
    private String nombre;
    private String apellidos;
    private String email;
    private Long creditos;
}
