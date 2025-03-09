package com.megacity.server.Service;

import com.megacity.server.Model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    public Review addReview(Review review);
    
    public List<Review> getReviewsByCabId(String cabId);
        
}