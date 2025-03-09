package com.megacity.server.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.megacity.server.Model.Booking;

@Service
public interface BookingService {

    Booking addBooking(Booking booking);
    List<Map<String, Object>> getAllBookings();
    Optional<Booking> getBookingById(String bookingId);
    List<Booking> getBookingsByCustomerId(String customerId);
    List<Booking> getBookingsByCabId(String cabId);
    Booking updateBooking(String bookingId, Booking booking);
    void deleteBooking(String bookingId); 
    public boolean isCabBooked(String cabId, String bookingDate, String bookingTime);
} 