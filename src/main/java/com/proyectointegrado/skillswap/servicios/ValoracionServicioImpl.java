package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Valoracion;

import java.util.List;
import java.util.Optional;

public class ValoracionServicioImpl implements ValoracionServicio{

    private final ValoracionServicio valoracionServicio;

    public ValoracionServicioImpl(ValoracionServicio valoracionServicio) {
        this.valoracionServicio = valoracionServicio;
    }

    @Override
    public List<Valoracion> obtenerValoraciones() {
        return valoracionServicio.obtenerValoraciones();
    }

    @Override
    public Optional<Valoracion> obtenerValoracion(Long id) {
        return valoracionServicio.obtenerValoracion(id);
    }

    @Override
    public Valoracion guardarValoracion(Valoracion valoracion) {
        return valoracionServicio.guardarValoracion(valoracion);
    }

    @Override
    public void eliminarValoracion(Long id) {
        valoracionServicio.eliminarValoracion(id);
    }
}
