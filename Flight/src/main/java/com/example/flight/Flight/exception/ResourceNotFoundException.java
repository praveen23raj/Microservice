package com.example.flight.Flight.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String flightNotFound) {
        System.out.println("not found");
        return;
    }
}
