package com.proyectointegrado.skillswap.controladores;

import com.proyectointegrado.skillswap.DTOs.ReservaRequestDTO;
import com.proyectointegrado.skillswap.conf.EmailServicio;
import com.proyectointegrado.skillswap.conf.JwtService;
import com.proyectointegrado.skillswap.entidades.Clase;
import com.proyectointegrado.skillswap.entidades.Reserva;
import com.proyectointegrado.skillswap.entidades.Usuario;
import com.proyectointegrado.skillswap.repositorios.UsuarioRepositorio;
import com.proyectointegrado.skillswap.servicios.ClaseServicioImpl;
import com.proyectointegrado.skillswap.servicios.ReservaServicioImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reservas")
public class ReservaControlador {

    private final ReservaServicioImpl reservaServicio;
    private final JwtService jwtService;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ClaseServicioImpl claseServicio;
    private final EmailServicio emailServicio;


    public ReservaControlador(ReservaServicioImpl reservaServicio, JwtService jwtService, UsuarioRepositorio usuarioRepositorio, ClaseServicioImpl claseServicio, EmailServicio emailServicio) {
        this.reservaServicio = reservaServicio;
        this.jwtService = jwtService;
        this.usuarioRepositorio = usuarioRepositorio;
        this.claseServicio = claseServicio;
        this.emailServicio = emailServicio;
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
    public ResponseEntity<?> crearReserva(@RequestBody ReservaRequestDTO reservaRequestDTO, HttpServletRequest request) {

        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Clase clase = claseServicio.obtenerClase(reservaRequestDTO.getIdClase()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (usuario.getCreditos() < clase.getPrecio()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No tienes suficientes créditos para reservar esta clase");
        }

        usuario.setCreditos(usuario.getCreditos() - clase.getPrecio());

        if (usuario.getCreditos() < 0) usuario.setCreditos(0L);

        Reserva reserva = Reserva.builder()
                .usuario(usuario)
                .clase(clase)
                .fecha(reservaRequestDTO.getFecha()).build();
        reservaServicio.guardarReserva(reserva);

//      Generación de videollamada
        String sala = "skillswap-" + UUID.randomUUID();
        String enlace = "https://meet.jit.si/" + sala;

        emailServicio.enviarEmail(usuario.getEmail(), enlace);

        return ResponseEntity.ok(enlace);
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
