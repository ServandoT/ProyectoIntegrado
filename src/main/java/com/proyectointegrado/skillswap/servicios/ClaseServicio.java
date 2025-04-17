package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Clase;

import java.util.List;
import java.util.Optional;

public interface ClaseServicio {
    public List<Clase> obtenerClases();
    public Optional<Clase> obtenerClase(Long id);
    public Clase guardarClase(Clase clase);
    public void eliminarClase(Long id);
}
