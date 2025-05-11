package com.proyectointegrado.skillswap.auth;

import com.proyectointegrado.skillswap.conf.JwtService;
import com.proyectointegrado.skillswap.entidades.Role;
import com.proyectointegrado.skillswap.entidades.Usuario;
import com.proyectointegrado.skillswap.repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepositorio repositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
         var usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .creditos(request.getCreditos())
                .password(passwordEncoder.encode(request.getPassword()))
                 .rol(Role.USER)
                .build();

         repositorio.save(usuario);
         var jwtToken = jwtService.generateToken(usuario);
         return AuthenticationResponse.builder()
                 .token(jwtToken)
                 .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var usuario = repositorio.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(usuario);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}
