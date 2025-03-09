package com.megacity.server.Repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.megacity.server.Model.Booking;

@Repository
public interface BookingRepository extends MongoRepository<Booking , String>{

    List<Booking> findByCustomerId(String customerId);
    List<Booking> findByCabId(String cabId);

    List<Booking> findByCabIdAndBookingDateAndBookingTime(String cabId, String bookingDate, String bookingTime);

 
    
}
