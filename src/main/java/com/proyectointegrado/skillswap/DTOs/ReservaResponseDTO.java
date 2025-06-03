package com.proyectointegrado.skillswap.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaResponseDTO {

    private String tituloClase;
    private LocalDate fecha;

}
