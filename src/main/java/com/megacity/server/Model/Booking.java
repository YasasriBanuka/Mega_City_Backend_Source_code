package com.megacity.server.Model;
/* import java.time.LocalDate;
import java.time.LocalTime;
 */
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "booking")
public class Booking {

    @Id
     private String bookingId;

     private String customerName;

     private String pickupLocation;

     private String destination;

     private String bookingDate;

     private String bookingTime;

     private String email;

     private String phoneNumber;

     private String status;

     private String cabId;

     private String customerId;

     private Double totalPrice;


    
}
