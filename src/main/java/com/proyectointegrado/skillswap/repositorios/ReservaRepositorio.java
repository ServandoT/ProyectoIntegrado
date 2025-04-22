package com.proyectointegrado.skillswap.repositorios;

import com.proyectointegrado.skillswap.entidades.Reserva;
import com.proyectointegrado.skillswap.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {

    public List<Reserva> findByUsuario(Usuario usuario);

}
