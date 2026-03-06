package com.example.booking.controller;


import com.example.booking.Dto.BookingWithFlightAndUserDto;
import com.example.booking.entity.Booking;
import com.example.booking.kafka.BookingProducer;
import com.example.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {


    private final BookingService bookingService;
    private final BookingProducer bookingProducer;


    @GetMapping("/CHECK")
    public ResponseEntity<String> CHECK() {
        return new ResponseEntity<>("hey worlimg", HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<BookingWithFlightAndUserDto> createBooking(@RequestBody Booking booking) throws Exception {
        log.info("Executed this method successfully without any error");
        BookingWithFlightAndUserDto createdBookingDto = bookingService.createBooking(booking);

        //kafka producer logic
        bookingProducer.sendBookingEvent(createdBookingDto);


        return new ResponseEntity<>(createdBookingDto, HttpStatus.CREATED);
    }


}
