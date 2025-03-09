package com.megacity.server.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.megacity.server.Model.Booking;
import com.megacity.server.Service.BookingService;

@RestController
@CrossOrigin(origins ="*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/auth/addbooking")
    public Booking addBooking(@RequestBody Booking booking) {
        return bookingService.addBooking(booking);
    }

    // Get all bookings
    @GetMapping("/getallbookings")
    public ResponseEntity<List<Map<String, Object>>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Get booking by ID
    @GetMapping("getbookingbyid/{bookingId}")
    public Optional<Booking> getBookingById(@PathVariable String bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    // Get bookings by customer ID
    @GetMapping("/auth/getbookingsbycustomer/{customerId}")
    public List<Booking> getBookingsByCustomerId(@PathVariable String customerId) {
        return bookingService.getBookingsByCustomerId(customerId);
    }

    @GetMapping("/getbookingsbycab/{cabId}")
    public List<Booking> getBookingsByCabId(@PathVariable String cabId) {
        return bookingService.getBookingsByCabId(cabId);
    }

    // Update booking
    @PatchMapping("/updatebooking/{bookingId}")
    public Booking updateBooking(@PathVariable String bookingId, @RequestBody Booking booking) {
        return bookingService.updateBooking(bookingId, booking);
    }
    

    // Delete booking
    @DeleteMapping("/deletebooking/{bookingId}")
    public void deleteBooking(@PathVariable String bookingId) {
        bookingService.deleteBooking(bookingId);
    }

    @GetMapping("/auth/checkAvailability")
    public boolean checkCabAvailability(
            @RequestParam String cabId,
            @RequestParam String bookingDate,
            @RequestParam String bookingTime) {
        return bookingService.isCabBooked(cabId, bookingDate, bookingTime);
    }
    
}
