package com.proyectointegrado.skillswap.controladores;

import com.proyectointegrado.skillswap.repositorios.UsuarioRepositorio;
import com.proyectointegrado.skillswap.servicios.UsuarioServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(usuarioServicio.getAllUsuarios());
    }

}
