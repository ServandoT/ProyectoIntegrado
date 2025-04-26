package com.proyectointegrado.skillswap.demo;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/demo")
public class DemoControlador {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hola desde la API de SkillSwap");
    }

}
