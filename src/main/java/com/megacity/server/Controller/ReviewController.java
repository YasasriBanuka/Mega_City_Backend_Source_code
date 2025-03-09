package com.megacity.server.Controller;

import com.megacity.server.Model.Review;
import com.megacity.server.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/auth/addreview")
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @GetMapping("/review/{cabId}")
    public ResponseEntity<List<Review>> getReviewsByCabId(@PathVariable String cabId) {
        List<Review> reviews = reviewService.getReviewsByCabId(cabId);
        return ResponseEntity.ok(reviews);
    }
}