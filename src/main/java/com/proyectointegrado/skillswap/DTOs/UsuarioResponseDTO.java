package com.proyectointegrado.skillswap.DTOs;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
}
