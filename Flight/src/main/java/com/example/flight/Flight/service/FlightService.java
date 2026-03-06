package com.example.flight.Flight.service;

import com.example.flight.Flight.entity.Flight;
import com.example.flight.Flight.exception.ResourceNotFoundException;
import com.example.flight.Flight.repo.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> searchFlights(String origin, String destination) {
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
    }

    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // New method to update available seats
    public Flight updateAvailableSeats(Long flightId, int seatsToDeduct) {
        Flight flight = getFlightById(flightId);

        if (flight.getAvailableSeats() < seatsToDeduct) {
            throw new RuntimeException("Not enough seats available.");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - seatsToDeduct);

        return flightRepository.save(flight); // Save the updated flight
    }
}
