package com.proyectointegrado.skillswap.repositorios;

import com.proyectointegrado.skillswap.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    public Optional<Usuario> findByEmail(String email);
}
