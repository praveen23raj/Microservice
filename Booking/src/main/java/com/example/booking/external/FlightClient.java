package com.example.booking.external;


import com.example.booking.Dto.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "flight-service", url = "http://localhost:8081")
public interface FlightClient {

    @GetMapping("/flights/{flightId}")
    FlightDTO getFlightById(@PathVariable("flightId") Long flightId);

    @PutMapping("/flights/{flightId}/{seats}")
    FlightDTO updateAvailableSeats(@PathVariable("flightId") Long flightId, @PathVariable("seats") int seats);
}
