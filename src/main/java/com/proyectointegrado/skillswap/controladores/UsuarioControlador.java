package com.proyectointegrado.skillswap.controladores;

import com.proyectointegrado.skillswap.DTOs.UsuarioModificar;
import com.proyectointegrado.skillswap.DTOs.UsuarioResponseDTO;
import com.proyectointegrado.skillswap.entidades.Role;
import com.proyectointegrado.skillswap.entidades.Usuario;
import com.proyectointegrado.skillswap.repositorios.UsuarioRepositorio;
import com.proyectointegrado.skillswap.servicios.UsuarioServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Devuelve una lista de todos los usuarios registrados en SkillSwap.",
            responses = {
                    @ApiResponse(
                            responseCode = "200", // <- Este campo es obligatorio
                            description = "Lista de usuarios obtenida correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "usuarioEjemplo",
                                            summary = "Ejemplo de usuario",
                                            value = "{ \"id\": 1, \"email\": \"juan.perez@example.com\", \"password\": \"1234seguro\", \"nombre\": \"Juan\", \"apellidos\": \"Pérez García\", \"créditos\": 10, \"reservas\": [], \"clases\": [] }"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<?> getUsuarios() {
        List<Usuario> usuarios = usuarioServicio.getAllUsuarios();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        List<UsuarioResponseDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class))
                .toList();

        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> isAdmin(@AuthenticationPrincipal Usuario usuario) {
        if (usuario == null) {
            return ResponseEntity.status(401).body("No estás autenticado");
        }

        boolean isAdmin = usuario.getRol().equals(Role.ADMIN);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("isAdmin", isAdmin);

        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@AuthenticationPrincipal Usuario usuario, @PathVariable Long id) {
        if (usuario == null) {
            return ResponseEntity.status(401).body("No estás autenticado");
        }

        usuarioServicio.deleteUsuario(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarUsuario(@AuthenticationPrincipal Usuario usuario, @PathVariable Long id, @RequestBody UsuarioModificar usuarioModificar) {
        if (usuario == null) {
            return ResponseEntity.status(401).body("No estás autenticado");
        }

        if (!usuario.getRol().equals(Role.ADMIN)) {
            return ResponseEntity.status(403).body("No tienes permiso para modificar este usuario");
        }

//        TODO manejar que el usuario con ese ID exista
        Usuario usuarioExistente = usuarioServicio.getUsuarioById(id).get();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(usuarioModificar, usuarioExistente);

        usuarioServicio.saveUsuario(usuarioExistente);

        return ResponseEntity.status(204).body("Usuario modificado correctamente");
    }

    @GetMapping("/puntos/pripios")
    public ResponseEntity<?> puntoPropios(@AuthenticationPrincipal Usuario usuario) {
        if (usuario == null) {
            return ResponseEntity.status(401).body("No estás autenticado");
        }

        Long puntos = usuario.getCreditos();

        return ResponseEntity.ok(puntos);
    }
}
