package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Clase;
import com.proyectointegrado.skillswap.repositorios.ClaseRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaseServicioImpl implements ClaseServicio{

    private final ClaseRepositorio claseRepositorio;

    public ClaseServicioImpl(ClaseRepositorio claseRepositorio) {
        this.claseRepositorio = claseRepositorio;
    }

    @Override
    public List<Clase> obtenerClases() {
        return claseRepositorio.findAll();
    }

    @Override
    public Optional<Clase> obtenerClase(Long id) {
        return claseRepositorio.findById(id);
    }

    @Override
    public Clase guardarClase(Clase clase) {
        return claseRepositorio.save(clase);
    }

    @Override
    public void eliminarClase(Long id) {
        claseRepositorio.deleteById(id);
    }
}
