package com.example.notification.notification.dto;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BookingConfirmedEvent {

    private Long bookingId;
    private String userEmail;
    private String flightNumber;
    private String origin;
    private String destination;
    private int numberOfSeats;

    // getters + setters + constructor
}
