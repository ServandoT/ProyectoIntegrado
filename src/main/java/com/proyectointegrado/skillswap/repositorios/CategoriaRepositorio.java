package com.proyectointegrado.skillswap.repositorios;

import com.proyectointegrado.skillswap.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}
