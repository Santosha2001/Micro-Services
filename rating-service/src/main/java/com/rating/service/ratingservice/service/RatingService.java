package com.rating.service.ratingservice.service;

import com.rating.service.ratingservice.entities.Rating;

import java.util.List;

public interface RatingService {

    // Create rating
    Rating createRating(Rating rating);

    // Get all rating
    List<Rating> getAllRatings();

    // get all rating by userid
    List<Rating> getAllRatingByUserId(String userId);

    // get all rating by hotel id
    List<Rating> getAllRatingByHotelId(String hotelId);

}
