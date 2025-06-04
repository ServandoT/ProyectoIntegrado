package com.proyectointegrado.skillswap.conf;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailServicio {

    private final JavaMailSender email;

    public EmailServicio(JavaMailSender email) {
        this.email = email;
    }

    public void enviarEmail(String para, String enlace, LocalDate fecha) {
        // Crear el mensaje
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("skillswap.daw@gmail.com");
        mensaje.setTo(para);
        mensaje.setSubject("Clase reservada");
        mensaje.setText("Has reservado la clase con éxito para el día " + fecha +".\nPincha aquí para acceder a la videollamada: " + enlace);

        // Enviar el email
        email.send(mensaje);
    }

    public void enviarEmailProfesor(String para, String enlace, LocalDate fecha) {
        // Crear el mensaje
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom("skillswap.daw@gmail.com");
        mensaje.setTo(para);
        mensaje.setSubject("Clase reservada");
        mensaje.setText("Un usuario ha reservado una de tus clases para el día " + fecha + "\n" + enlace);

        // Enviar el email
        email.send(mensaje);
    }

}
