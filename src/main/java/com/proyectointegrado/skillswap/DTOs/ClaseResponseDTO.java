package com.proyectointegrado.skillswap.DTOs;

import com.proyectointegrado.skillswap.entidades.Valoracion;
import lombok.Data;

import java.util.List;

@Data
public class ClaseResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String duracion; // Formato HH:mm
    private Long precio;
    private String idioma;
    private List<CategoriaResponseDTO> categorias; // Solo nombres
    private UsuarioResponseDTO profesor; // DTO del profesor
    private List<ValoracionResponseDTO> valoraciones; // Lista de valoraciones de la clase, sin detalles del usuario que las hizo
}
