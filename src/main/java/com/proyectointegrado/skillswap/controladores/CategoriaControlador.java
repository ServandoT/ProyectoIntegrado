package com.proyectointegrado.skillswap.controladores;

import com.proyectointegrado.skillswap.entidades.Categoria;
import com.proyectointegrado.skillswap.servicios.CategoriaServicio;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(categoriaServicio.obtenerCategorias());
    }

    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {

//        TODO comprobar que hay usuario logado y que es admin

        return ResponseEntity.ok(categoriaServicio.guardarCategoria(categoria));
    }

//    TODO comprobar lo del ID si está bien
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@RequestParam Long id) {
//        TODO comprobar que hay usuario logado y que es admin

        categoriaServicio.eliminarCategoria(id);

        return ResponseEntity.ok().build();
    }

//    TODO implementar actualizar categoria
}
