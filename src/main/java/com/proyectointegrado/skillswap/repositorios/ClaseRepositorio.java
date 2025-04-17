package com.proyectointegrado.skillswap.repositorios;

import com.proyectointegrado.skillswap.entidades.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaseRepositorio extends JpaRepository<Clase, Long> {
}
