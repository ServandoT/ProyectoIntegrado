package com.proyectointegrado.skillswap;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // Configura la aplicación Spring Boot para que se ejecute en un servidor servlet externo
        return application.sources(SkillSwapApplication.class);
    }

}
