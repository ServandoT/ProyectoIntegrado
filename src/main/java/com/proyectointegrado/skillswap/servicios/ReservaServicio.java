package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Reserva;
import com.proyectointegrado.skillswap.entidades.Usuario;

import java.util.List;
import java.util.Optional;

public interface ReservaServicio {
    public List<Reserva> obtenerReservas();
    public Optional<Reserva> obtenerReserva(Long id);
    public Reserva guardarReserva(Reserva reserva);
    public void eliminarReserva(Long id);
    public List<Reserva> obtenerReservasPorUsuario(Usuario usuario);
}
