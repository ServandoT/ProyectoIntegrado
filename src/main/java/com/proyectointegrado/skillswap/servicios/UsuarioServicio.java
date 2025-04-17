package com.proyectointegrado.skillswap.servicios;

import com.proyectointegrado.skillswap.entidades.Usuario;
import com.proyectointegrado.skillswap.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

private final UsuarioRepositorio usuarioRepositorio;


    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepositorio.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }

}
