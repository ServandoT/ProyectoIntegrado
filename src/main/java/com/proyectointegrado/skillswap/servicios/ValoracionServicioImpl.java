package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Valoracion;
import com.proyectointegrado.skillswap.repositorios.ValoracionRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValoracionServicioImpl implements ValoracionServicio{

    private final ValoracionRepositorio valoracionRepositorio;

    public ValoracionServicioImpl(ValoracionRepositorio valoracionRepositorio) {
        this.valoracionRepositorio = valoracionRepositorio;
    }

    @Override
    public List<Valoracion> obtenerValoraciones() {
        return valoracionRepositorio.findAll();
    }

    @Override
    public Optional<Valoracion> obtenerValoracion(Long id) {
        return valoracionRepositorio.findById(id);
    }

    @Override
    public Valoracion guardarValoracion(Valoracion valoracion) {
        return valoracionRepositorio.save(valoracion);
    }

    @Override
    public void eliminarValoracion(Long id) {
        valoracionRepositorio.deleteById(id);
    }
}
