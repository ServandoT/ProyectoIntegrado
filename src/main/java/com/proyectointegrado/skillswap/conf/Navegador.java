package com.proyectointegrado.skillswap.conf;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

@Component
public class Navegador {

    @PostConstruct
    public void lanzarNavegador() {
        String url = "http://localhost:1234/swagger-ui/index.html#/";

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                String os = System.getProperty("os.name").toLowerCase();

                if (os.contains("win")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } else if (os.contains("mac")) {
                    Runtime.getRuntime().exec("open " + url);
                } else if (os.contains("nix") || os.contains("nux")) {
                    Runtime.getRuntime().exec("xdg-open " + url);
                } else {
                    System.out.println("No se pudo determinar el sistema operativo para abrir el navegador.");
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo abrir el navegador: " + e.getMessage());
        }
    }
}
