package com.proyectointegrado.skillswap.controladores;

import com.proyectointegrado.skillswap.DTOs.CategoriaRequestDTO;
import com.proyectointegrado.skillswap.DTOs.CategoriaResponseDTO;
import com.proyectointegrado.skillswap.entidades.Categoria;
import com.proyectointegrado.skillswap.entidades.Role;
import com.proyectointegrado.skillswap.entidades.Usuario;
import com.proyectointegrado.skillswap.servicios.CategoriaServicio;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;

    public CategoriaControlador(CategoriaServicio categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping()
    public ResponseEntity<?> listarCategorias() {
        categoriaServicio.obtenerCategorias();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        return ResponseEntity.ok(categoriaServicio.obtenerCategorias().stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaResponseDTO.class))
                .toList());
    }

    @PostMapping
    public ResponseEntity<?> crearCategoria(@AuthenticationPrincipal Usuario usuario, @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        if (usuario == null || !usuario.getRol().equals(Role.ADMIN)) {
            return ResponseEntity.status(401).body("No tienes permisos para crear una categoría");
        }

        Categoria categoria = new Categoria();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(categoriaRequestDTO, categoria);

        categoriaServicio.guardarCategoria(categoria);
//        TODO comprobar que hay usuario logado y que es admin

        return ResponseEntity.ok(categoria);
    }

//    TODO comprobar lo del ID si está bien
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@AuthenticationPrincipal Usuario usuario, @PathVariable Long id) {
        if (usuario == null || !usuario.getRol().equals(Role.ADMIN)) {
            return ResponseEntity.status(401).body("No tienes permisos para crear una categoría");
        }

        categoriaServicio.eliminarCategoria(id);

        return ResponseEntity.ok().build();
    }

//    TODO implementar actualizar categoria
}
