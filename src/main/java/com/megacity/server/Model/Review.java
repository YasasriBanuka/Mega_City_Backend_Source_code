package com.megacity.server.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "reviews")
public class Review {
    
    @Id
    private String reviewId; // Unique identifier for the review
    private String cabId; // ID of the cab being reviewed
    private String customerId; // ID of the user who wrote the review
    private int rating; // Rating out of 5
    private String reviewText; // The review text
 // You can add additional fields like timestamp, etc.
}