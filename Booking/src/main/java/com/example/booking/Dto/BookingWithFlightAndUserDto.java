package com.example.booking.Dto;



import com.example.booking.entity.Booking;


public class BookingWithFlightAndUserDto {

    private Booking booking;
    private FlightDTO flight;
    private UserDto user;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public FlightDTO getFlight() {
        return flight;
    }

    public void setFlight(FlightDTO flight) {
        this.flight = flight;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }



    // Constructor
    public BookingWithFlightAndUserDto(Booking booking, FlightDTO flight, UserDto user) {
        this.booking = booking;
        this.flight = flight;
        this.user = user;
    }


}
