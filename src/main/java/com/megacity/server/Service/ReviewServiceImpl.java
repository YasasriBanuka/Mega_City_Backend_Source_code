package com.megacity.server.Service;

import com.megacity.server.Model.Review;
import com.megacity.server.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByCabId(String cabId) {
        return reviewRepository.findByCabId(cabId);
    }
}