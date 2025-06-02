package com.rating.service.ratingservice.controller;

import com.rating.service.ratingservice.entities.Rating;
import com.rating.service.ratingservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // create rating
    @PostMapping("create")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {

        Rating savedRating = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    // get all ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> allRatings = ratingService.getAllRatings();
        return ResponseEntity.ok(allRatings);
    }

    // get ratings by user id
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId) {
        List<Rating> ratings = ratingService.getAllRatingByUserId(userId);
        return ResponseEntity.ok(ratings);
    }

    // get rating by hotel id
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId) {
        List<Rating> ratings = ratingService.getAllRatingByHotelId(hotelId);
        return ResponseEntity.ok(ratings);
    }
}
