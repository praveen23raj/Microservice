package com.example.booking.service;

import com.example.booking.Dto.BookingWithFlightAndUserDto;
import com.example.booking.Dto.FlightDTO;
import com.example.booking.Dto.UserDto;
import com.example.booking.entity.Booking;
import com.example.booking.external.FlightClient;
import com.example.booking.external.UserClient;
import com.example.booking.repository.BookingRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;
    private final UserClient userClient;

    @Autowired
    public BookingService(BookingRepository bookingRepository, FlightClient flightClient, UserClient userClient) {
        this.bookingRepository = bookingRepository;
        this.flightClient = flightClient;
        this.userClient = userClient;
    }

    public BookingWithFlightAndUserDto createBooking(Booking booking) {
        // Fetch flight details using Feign Client
        FlightDTO flight;
        try {
            flight = flightClient.getFlightById(booking.getFlightId());
        } catch (FeignException e) {
            throw new RuntimeException("Error fetching flight details: " + e.getMessage());
        }
        if (flight == null) {
            throw new RuntimeException("Flight not found.");
        }

        // Check if there are enough available seats
        if (flight.getAvailableSeats() < booking.getNumberOfSeats()) {
            throw new RuntimeException("Not enough seats available.");
        }

        // Deduct booked seats from available seats using Feign Client
        try {
            flightClient.updateAvailableSeats(booking.getFlightId(), booking.getNumberOfSeats());
        } catch (FeignException e) {
            throw new RuntimeException("Error updating available seats: " + e.getMessage());
        }

        // Fetch user details using Feign Client
        UserDto user;
        try {
            user = userClient.getUserById(booking.getUserId());
        } catch (FeignException e) {
            throw new RuntimeException("Error fetching user details: " + e.getMessage());
        }
        if (user == null) {
            throw new RuntimeException("User not found.");
        }

        // Set additional booking details
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        // Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // Map Flight, User, and Booking to BookingWithFlightAndUserDto
        return new BookingWithFlightAndUserDto(savedBooking, flight, user);
    }
}
