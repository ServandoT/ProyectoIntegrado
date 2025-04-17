package com.proyectointegrado.skillswap.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private Long creditos;
}
