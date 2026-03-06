package com.example.booking.kafka;

import com.example.booking.Dto.BookingConfirmedEvent;
import com.example.booking.Dto.BookingWithFlightAndUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public BookingProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookingEvent(BookingWithFlightAndUserDto dto) throws Exception {

        BookingConfirmedEvent event = mapToEvent(dto);
        String json = mapper.writeValueAsString(event);

        kafkaTemplate.send("booking-confirmed", json);
    }
    public BookingConfirmedEvent mapToEvent(BookingWithFlightAndUserDto dto) {

        return new BookingConfirmedEvent(
                dto.getBooking().getId(),
                dto.getUser().getEmail(),
                dto.getFlight().getFlightNumber(),
                dto.getFlight().getOrigin(),
                dto.getFlight().getDestination(),
                dto.getBooking().getNumberOfSeats()
        );
    }

}
