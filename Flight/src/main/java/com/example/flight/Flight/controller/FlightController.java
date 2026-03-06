package com.example.flight.Flight.controller;

import com.example.flight.Flight.entity.Flight;
import com.example.flight.Flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> all(){
        List<Flight> flights = flightService.getAllFlights();
        return  ResponseEntity.ok(flights);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(@RequestParam String origin, @RequestParam String destination) {
        List<Flight> flights = flightService.searchFlights(origin, destination);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight createdFlight = flightService.createFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
                flightService.deleteFlight(id);
                return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}/{seats}")
    public ResponseEntity<Flight> updateAvailableSeats(@PathVariable Long id, @PathVariable int seats) {
        Flight updatedFlight = flightService.updateAvailableSeats(id, seats);
        return ResponseEntity.ok(updatedFlight);
    }




}