package com.proyectointegrado.skillswap.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Prueba {

    @GetMapping("/prueba")
    public String prueba() {
        return "redirect:/index.html";
    }
}
