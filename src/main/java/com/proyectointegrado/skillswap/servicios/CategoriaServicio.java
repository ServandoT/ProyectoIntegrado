package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaServicio {
    public List<Categoria> obtenerCategorias();
    public Optional<Categoria> obtenerCategoria(Long id);
    public Categoria guardarCategoria(Categoria categoria);
    public void eliminarCategoria(Long id);
    public  Categoria obtenerCategoriaByNombre(String nombre);
}
