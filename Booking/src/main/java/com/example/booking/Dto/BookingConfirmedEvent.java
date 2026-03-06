package com.example.booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingConfirmedEvent {

    private Long bookingId;
    private String userEmail;
    private String flightNumber;
    private String origin;
    private String destination;
    private int numberOfSeats;
}
