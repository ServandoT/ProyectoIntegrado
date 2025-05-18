package com.proyectointegrado.skillswap.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class ClaseRequestDTO {
    private String titulo;
    private String descripcion;
    private String duracion; // Formato HH:mm
    private Long precio;
    private String idioma;
    private List<String> categorias; // Solo nombres
}
