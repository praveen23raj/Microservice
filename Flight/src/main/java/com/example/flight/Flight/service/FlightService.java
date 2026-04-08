package com.example.flight.Flight.service;

import com.example.flight.Flight.entity.Flight;
import com.example.flight.Flight.exception.ResourceNotFoundException;
import com.example.flight.Flight.repo.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Cacheable(value = "flights", key = "#origin + '-' + #destination")
    public List<Flight> searchFlights(String origin, String destination) {
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    @Cacheable(value = "flight", key = "#id")
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
    }

    @CacheEvict(value = {"flights", "flight"}, allEntries = true)
    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @CacheEvict(value = {"flights", "flight"}, allEntries = true)
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    @Cacheable(value = "flights", key = "'allFlights'")
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @CacheEvict(value = {"flights", "flight"}, allEntries = true)
    public Flight updateAvailableSeats(Long flightId, int seatsToDeduct) {

        Flight flight = getFlightById(flightId);

        if (flight.getAvailableSeats() < seatsToDeduct) {
            throw new RuntimeException("Not enough seats available.");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - seatsToDeduct);

        return flightRepository.save(flight);
    }
}