package com.example.notification.notification.kafka;

import com.example.notification.notification.dto.BookingConfirmedEvent;
import com.example.notification.notification.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookingConsumer {

    private final ObjectMapper mapper = new ObjectMapper();
    private final EmailService emailService;

    public BookingConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "booking-confirmed", groupId = "notification-group")
    public void consumeBooking(String message) throws Exception {

        BookingConfirmedEvent event = mapper.readValue(message, BookingConfirmedEvent.class);

        System.out.println("📩 Booking Event Received: " + event);

        // Send Email
        emailService.sendBookingEmail(event);
    }
}
