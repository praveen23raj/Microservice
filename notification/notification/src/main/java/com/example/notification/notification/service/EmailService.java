package com.example.notification.notification.service;

import com.example.notification.notification.dto.BookingConfirmedEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBookingEmail(BookingConfirmedEvent event) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(event.getUserEmail());
        mail.setSubject("Flight Booking Confirmed ✈️");
        mail.setText(
                "Your booking is confirmed!\n\n" +
                        "Booking ID: " + event.getBookingId() +
                        "\nFlight: " + event.getFlightNumber() +
                        "\nFrom: " + event.getOrigin() +
                        "\nTo: " + event.getDestination() +
                        "\nSeats: " + event.getNumberOfSeats()
        );

        mailSender.send(mail);
    }
}
