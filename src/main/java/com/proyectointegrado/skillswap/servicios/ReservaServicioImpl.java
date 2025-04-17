package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Reserva;
import com.proyectointegrado.skillswap.repositorios.ReservaRepositorio;

import java.util.List;
import java.util.Optional;

public class ReservaServicioImpl implements  ReservaServicio{

    private final ReservaRepositorio reservaRepositorio;

    public ReservaServicioImpl(ReservaRepositorio reservaRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
    }

    @Override
    public List<Reserva> obtenerReservas() {
        return reservaRepositorio.findAll();
    }

    @Override
    public Optional<Reserva> obtenerReserva(Long id) {
        return reservaRepositorio.findById(id);
    }

    @Override
    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepositorio.deleteById(id);
    }
}
