package com.proyectointegrado.skillswap.DTOs;

import com.proyectointegrado.skillswap.entidades.Role;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private Long creditos;
    private Role rol;
}
