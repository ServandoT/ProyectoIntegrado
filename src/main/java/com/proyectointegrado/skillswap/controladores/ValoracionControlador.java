package com.proyectointegrado.skillswap.controladores;

import com.proyectointegrado.skillswap.DTOs.ValoracionRequestDTO;
import com.proyectointegrado.skillswap.conf.JwtService;
import com.proyectointegrado.skillswap.entidades.Clase;
import com.proyectointegrado.skillswap.entidades.Usuario;
import com.proyectointegrado.skillswap.entidades.Valoracion;
import com.proyectointegrado.skillswap.repositorios.UsuarioRepositorio;
import com.proyectointegrado.skillswap.servicios.ClaseServicio;
import com.proyectointegrado.skillswap.servicios.ValoracionServicio;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/valoraciones")
public class ValoracionControlador {

    private final ValoracionServicio valoracionServicio;
    private final JwtService jwtService;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ClaseServicio claseServicio;

    public ValoracionControlador(ValoracionServicio valoracionServicio, JwtService jwtService, UsuarioRepositorio usuarioRepositorio, ClaseServicio claseServicio) {
        this.valoracionServicio = valoracionServicio;
        this.jwtService = jwtService;
        this.usuarioRepositorio = usuarioRepositorio;
        this.claseServicio = claseServicio;
    }

    @GetMapping("{idClase}")
    public ResponseEntity<?> getValoracionesClase(HttpServletRequest request, @PathVariable Long idClase) {
        Clase clase = claseServicio.obtenerClase(idClase)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clase no encontrada"));

        List<Valoracion> valoraciones = clase.getValoraciones();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        List<Valoracion> valoracionesDTO = valoraciones.stream()
                .map(valoracion -> modelMapper.map(valoracion, Valoracion.class))
                .toList();

        return ResponseEntity.ok(valoracionesDTO);
    }

    @PostMapping("{idClase}")
    public ResponseEntity<?> crearValoracion(@PathVariable Long idClase, @RequestBody ValoracionRequestDTO valoracionRequestDTO, HttpServletRequest request) {
        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Clase clase = claseServicio.obtenerClase(idClase)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clase no encontrada"));

        Valoracion valoracion = new Valoracion();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(valoracionRequestDTO, valoracion);
        valoracion.setClase(clase);
        valoracionServicio.guardarValoracion(valoracion);

        return ResponseEntity.status(HttpStatus.CREATED).body("Valoración creada correctamente");

    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader != null && authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : null;
    }
}
