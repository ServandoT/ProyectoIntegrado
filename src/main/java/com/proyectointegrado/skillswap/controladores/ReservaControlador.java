package com.proyectointegrado.skillswap.controladores;

import com.proyectointegrado.skillswap.conf.JwtService;
import com.proyectointegrado.skillswap.entidades.Reserva;
import com.proyectointegrado.skillswap.entidades.Usuario;
import com.proyectointegrado.skillswap.repositorios.UsuarioRepositorio;
import com.proyectointegrado.skillswap.servicios.ReservaServicioImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaControlador {

    private final ReservaServicioImpl reservaServicio;
    private final JwtService jwtService;
    private final UsuarioRepositorio usuarioRepositorio;


    public ReservaControlador(ReservaServicioImpl reservaServicio, JwtService jwtService, UsuarioRepositorio usuarioRepositorio) {
        this.reservaServicio = reservaServicio;
        this.jwtService = jwtService;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @GetMapping
    public ResponseEntity<?> listarReservas() {
        return ResponseEntity.ok(reservaServicio.obtenerReservas());
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> listarReservasUsuario(HttpServletRequest request) {

        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(reservaServicio.obtenerReservasPorUsuario(usuario));
    }


    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody Reserva reserva, HttpServletRequest request) {

        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

//        TODO faltan cosas
        return ResponseEntity.ok(reservaServicio.guardarReserva(reserva));
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarReserva(@RequestParam Long id, HttpServletRequest request) {

        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<Reserva> reserva = reservaServicio.obtenerReserva(id);

        if (reserva.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

//        TODO añadir que el usuario sea el que hizo la reserva o admin

        reservaServicio.eliminarReserva(id);
        return ResponseEntity.ok("Reserva eliminada");
    }

    @PutMapping
    public ResponseEntity<?> modificarReserva(@RequestBody Reserva reserva, HttpServletRequest request) {
        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<Reserva> reservaExistente = reservaServicio.obtenerReserva(reserva.getId());

        if (reservaExistente.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

//        TODO cambiar la modificación entera a partir de aquí
        return ResponseEntity.ok(reservaServicio.guardarReserva(reserva));
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader != null && authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : null;
    }

}
