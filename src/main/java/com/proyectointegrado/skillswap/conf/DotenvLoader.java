package com.proyectointegrado.skillswap.conf;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

// TODO mirar si funciona
@Configuration
public class DotenvLoader {

    public void loadDotenv() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }
}
