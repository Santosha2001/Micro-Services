package com.rating.service.ratingservice.service;

import com.rating.service.ratingservice.entities.Rating;

import java.util.List;

public interface RatingService {

    //create rating
    Rating createRating(Rating rating);

    //get all rating
    List<Rating> getAllRatings();

    //get all rating by userid
    List<Rating> getRatingByUserId(String userId);

    //get all rating by hotel id
    List<Rating> getRatingByHotelId(String hotelId);

}
