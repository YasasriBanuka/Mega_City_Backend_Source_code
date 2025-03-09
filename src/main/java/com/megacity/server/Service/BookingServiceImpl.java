package com.megacity.server.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled; 
import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;

import com.megacity.server.Model.Booking;
import com.megacity.server.Model.Cabs;
import com.megacity.server.Repository.BookingRepository;
import com.megacity.server.Repository.CabsRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CabsRepository cabsRepository;

    @Autowired
    private JavaMailSender mailSender;



    @Override
    public Booking addBooking(Booking booking) {

        return bookingRepository.save(booking);
    }


    public boolean isCabBooked(String cabId, String bookingDate, String bookingTime) {
        List<Booking> bookings = bookingRepository.findByCabIdAndBookingDateAndBookingTime(cabId, bookingDate, bookingTime);
        return !bookings.isEmpty(); // If bookings exist, the cab is already booked
    }

    @Override
    public void deleteBooking(String bookingId) {
        bookingRepository.deleteById(bookingId);
        
    }

    @Override
    public List<Map<String, Object>> getAllBookings() {
        // Fetch all bookings
        List<Booking> bookingList = bookingRepository.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();
    
        // Loop through all bookings to extract relevant information
        for (Booking booking : bookingList) {
            Map<String, Object> bookingResponse = new HashMap<>();
    
            // Add booking details
            bookingResponse.put("bookingId", booking.getBookingId());
            bookingResponse.put("customerName", booking.getCustomerName());
            bookingResponse.put("pickupLocation", booking.getPickupLocation());
            bookingResponse.put("destination", booking.getDestination());
            bookingResponse.put("bookingDate", booking.getBookingDate().toString());  // Convert to String
            bookingResponse.put("bookingTime", booking.getBookingTime().toString());  // Convert to String
            bookingResponse.put("email", booking.getEmail());
            bookingResponse.put("phoneNumber", booking.getPhoneNumber());
            bookingResponse.put("status", booking.getStatus());
            bookingResponse.put("customerId", booking.getCustomerId());
            bookingResponse.put("totalPrice", booking.getTotalPrice());

            Optional<Cabs> category = cabsRepository.findById(booking.getCabId());
            bookingResponse.put("cabNumberPlate", category.map(Cabs::getCabNumberPlate).orElse("Unknown"));
    
            // Add the booking response to the final list
            responseList.add(bookingResponse);
        }
        return responseList;
    }
    

    @Override
    public Optional<Booking> getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public List<Booking> getBookingsByCustomerId(String customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public Booking updateBooking(String bookingId, Booking booking) {
        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking.isPresent()) {
            Booking updatedBooking = existingBooking.get();
            if (booking.getStatus() != null) {
                updatedBooking.setStatus(booking.getStatus());
    
                // Send bill email if status is updated to "completed"
                if ("completed".equalsIgnoreCase(booking.getStatus())) {
                    sendBillEmail(updatedBooking);
                }
            }
            // Update other fields as necessary
            return bookingRepository.save(updatedBooking);
        }
        return null;
    }


    @Override
    public List<Booking> getBookingsByCabId(String cabId) {
        return bookingRepository.findByCabId(cabId);
        
    }


@Scheduled(fixedRate = 1000)
@Transactional
public void updateCabAvailability() {
    // Adjust to Sri Lanka timezone
    ZoneId sriLankaZoneId = ZoneId.of("Asia/Colombo");
    LocalDateTime now = LocalDateTime.now(sriLankaZoneId);  // Current time in Sri Lanka timezone
    LocalDate today = now.toLocalDate();
    LocalTime currentTime = now.toLocalTime().truncatedTo(ChronoUnit.SECONDS); // Truncate milliseconds

    List<Booking> bookings = bookingRepository.findAll();

    for (Booking booking : bookings) {
        if (booking.getStatus().equalsIgnoreCase("Confirmed")) {
            // Assuming bookingDate and bookingTime are in Sri Lanka time (Asia/Colombo)
            LocalDate bookingDate = LocalDate.parse(booking.getBookingDate());
            LocalTime bookingTime = LocalTime.parse(booking.getBookingTime()).truncatedTo(ChronoUnit.SECONDS); // Truncate milliseconds

            String cabId = booking.getCabId();

            // Compare the booking date and time with the current date and time
            if (bookingDate.equals(today) && bookingTime.equals(currentTime)) {
                Optional<Cabs> cab = cabsRepository.findById(cabId);
                cab.ifPresent(c -> {
                    c.setAvailabilityStatus("Unavailable");
                    cabsRepository.save(c);
                });
            }
        }
    }
}





private void sendBillEmail(Booking booking) {
    String to = booking.getEmail(); // Get email from the booking
    String subject = "Your Ride Bill - Megacity Cab Services";

    // HTML Content for the Bill Email
    String htmlContent = "<html>"
        + "<body style='font-family: Arial, sans-serif; color: #333;'>"
        + "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
        + "<h2 style='color: #007BFF;'>Ride Completed!</h2>"
        + "<p>Dear " + booking.getCustomerName() + ",</p>"
        + "<p>Your ride with <strong>Megacity Cab Services</strong> has been completed. Below are the details of your ride and the bill:</p>"
        + "<h3 style='color: #007BFF;'>Ride Details:</h3>"
        + "<ul>"
        + "<li><strong>Booking ID:</strong> " + booking.getBookingId() + "</li>"
        + "<li><strong>Pickup Location:</strong> " + booking.getPickupLocation() + "</li>"
        + "<li><strong>Destination:</strong> " + booking.getDestination() + "</li>"
        + "<li><strong>Booking Date:</strong> " + booking.getBookingDate() + "</li>"
        + "<li><strong>Booking Time:</strong> " + booking.getBookingTime() + "</li>"
        + "<li><strong>Total Price:</strong> Rs." + booking.getTotalPrice() + "</li>"
        + "</ul>"
        + "<h3 style='color: #007BFF;'>Payment Instructions:</h3>"
        + "<p>Please pay the total amount of <strong>Rs. " + booking.getTotalPrice() + "</strong> in <strong>cash</strong> to the driver.</p>"
        + "<h3 style='color: #007BFF;'>Important Notes:</h3>"
        + "<ul>"
        + "<li>Ensure you have the exact amount ready for payment.</li>"
        + "<li>If you have any questions or concerns, please contact our support team at <a href='mailto:megacity@gmail.com'>megacity@gmail.com</a>.</li>"
        + "</ul>"
        + "<p>Thank you for choosing Megacity Cab Services. We look forward to serving you again!</p>"
        + "<p>Best Regards,</p>"
        + "<p><strong>The Megacity Cab Services Team</strong></p>"
        + "<p>Email: <a href='mailto:megacity@gmail.com'>megacity@gmail.com</a></p>"
        + "<p>Phone: +94 764 610 843</p>"
        + "</div>"
        + "</body>"
        + "</html>";

    try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("banukadias5@gmail.com"); // Set the "from" address explicitly
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // Set the email content as HTML

        mailSender.send(message);
        System.out.println("Bill email successfully sent to: " + to);
    } catch (MessagingException e) {
        System.err.println("Error while sending bill email: " + e.getMessage());
        e.printStackTrace();
    }
}
    
}
