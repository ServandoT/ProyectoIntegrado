package com.proyectointegrado.skillswap.conf;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicio {

    private final JavaMailSender email;

    public EmailServicio(JavaMailSender email) {
        this.email = email;
    }

    public void enviarEmail(String para, String enlace) {
        // Crear el mensaje
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("skillswap.daw@gmail.com");
        mensaje.setTo(para);
        mensaje.setSubject("Clase reservada");
        mensaje.setText("Has reservado la clase con éxito.\nPincha aquí para acceder a la videollamada: " + enlace);

        // Enviar el email
        email.send(mensaje);
    }

}
