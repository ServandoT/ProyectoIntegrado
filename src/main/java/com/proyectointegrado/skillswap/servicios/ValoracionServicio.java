package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Valoracion;

import java.util.List;
import java.util.Optional;

public interface ValoracionServicio {
    public List<Valoracion> obtenerValoraciones();
    public Optional<Valoracion> obtenerValoracion(Long id);
    public Valoracion guardarValoracion(Valoracion valoracion);
    public void eliminarValoracion(Long id);
}
