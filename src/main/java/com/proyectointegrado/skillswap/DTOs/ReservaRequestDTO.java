package com.proyectointegrado.skillswap.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequestDTO {
    private LocalDate fecha;
    private Long idClase;
}
