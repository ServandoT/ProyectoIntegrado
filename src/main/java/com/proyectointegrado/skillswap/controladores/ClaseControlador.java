package com.proyectointegrado.skillswap.controladores;

import com.proyectointegrado.skillswap.DTOs.ClaseRequestDTO;
import com.proyectointegrado.skillswap.DTOs.ClaseResponseDTO;
import com.proyectointegrado.skillswap.DTOs.UsuarioResponseDTO;
import com.proyectointegrado.skillswap.conf.JwtService;
import com.proyectointegrado.skillswap.entidades.Categoria;
import com.proyectointegrado.skillswap.entidades.Clase;
import com.proyectointegrado.skillswap.entidades.Usuario;
import com.proyectointegrado.skillswap.repositorios.UsuarioRepositorio;
import com.proyectointegrado.skillswap.servicios.CategoriaServicio;
import com.proyectointegrado.skillswap.servicios.ClaseServicioImpl;
import com.proyectointegrado.skillswap.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/clases")
public class ClaseControlador {

    private final ClaseServicioImpl claseServicio;
    private final JwtService jwtService;
    private final UsuarioServicio usuarioServicio;
    private final CategoriaServicio categoriaServicio;

    public ClaseControlador(ClaseServicioImpl claseServicio, JwtService jwtService, UsuarioServicio usuarioServicio, CategoriaServicio categoriaServicio) {
        this.claseServicio = claseServicio;
        this.jwtService = jwtService;
        this.usuarioServicio = usuarioServicio;
        this.categoriaServicio = categoriaServicio;
    }

    @GetMapping
    public ResponseEntity<?> listarClases() {
        List<Clase> clases = claseServicio.obtenerClases();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        List<ClaseResponseDTO> clasesDTO = clases.stream()
                .map(clase -> modelMapper.map(clase, ClaseResponseDTO.class))
                .toList();

        return ResponseEntity.ok(clasesDTO);
    }

    @PostMapping
    public ResponseEntity<?> crearClase(@RequestBody ClaseRequestDTO claseRequestDTO, HttpServletRequest request) {

        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioServicio.getUsuarioByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

//        TODO revisar
        Clase clase = new Clase();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(claseRequestDTO, clase);
        clase.setProfesor(usuario);
//        Mapear todas las categorías a partir del nombre
//        TODO cambiar a Set de categorias en vez de List
        List<Categoria> categorias = claseRequestDTO.getCategorias().stream()
                .map(nombre -> categoriaServicio.obtenerCategoriaByNombre(nombre))
                .toList();
        clase.setCategorias(categorias);
        clase.setValoraciones(List.of());
        clase.setId(null);

        claseServicio.guardarClase(clase);
        return ResponseEntity.ok().build();
    }

//    TODO implementar que también pueda si es admin
    @DeleteMapping
    public ResponseEntity<?> eliminarClase(@RequestParam Long id, HttpServletRequest request) {

        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioServicio.getUsuarioByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<Clase> clase = claseServicio.obtenerClase(id);

        if (clase.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Long idProfesor = clase.get().getProfesor().getId();
        Long idUsuario = usuario.getId();

        if (!idProfesor.equals(idUsuario)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        claseServicio.eliminarClase(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    public ResponseEntity<?> actualizarClase(@RequestParam Long id, @RequestBody Clase clase, HttpServletRequest request) {

        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioServicio.getUsuarioByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Optional<Clase> claseExistente = claseServicio.obtenerClase(id);

//        TODO Necesito el id del existente?
        if (claseExistente.isPresent() && claseExistente.get().getProfesor().getId().equals(usuario.getId())) {
            Clase claseNueva = Clase.builder()
                    .id(clase.getId())
                    .titulo(clase.getTitulo())
                    .categorias(clase.getCategorias())
                    .precio(clase.getPrecio())
                    .profesor(clase.getProfesor())
                    .idioma(clase.getIdioma())
                    .valoraciones(clase.getValoraciones())
                    .build();

            return ResponseEntity.ok(claseServicio.guardarClase(claseNueva));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/propias")
    private ResponseEntity<?> misClases(HttpServletRequest request) {
        String email = jwtService.extractUsername(getToken(request));
        Usuario usuario = usuarioServicio.getUsuarioByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Clase> clases = usuario.getClases();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        List<ClaseResponseDTO> clasesDTO = clases.stream()
                .map(clase -> modelMapper.map(clase, ClaseResponseDTO.class))
                .toList();

        return ResponseEntity.ok(clasesDTO);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader != null && authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : null;
    }
}
