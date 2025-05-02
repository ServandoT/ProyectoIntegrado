package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Categoria;
import com.proyectointegrado.skillswap.repositorios.CategoriaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;

    public CategoriaServicioImpl(CategoriaRepositorio categoriaRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
    }

    @Override
    public List<Categoria> obtenerCategorias() {
        return categoriaRepositorio.findAll();
    }

    @Override
    public Optional<Categoria> obtenerCategoria(Long id) {
        return categoriaRepositorio.findById(id);
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepositorio.deleteById(id);
    }
}
