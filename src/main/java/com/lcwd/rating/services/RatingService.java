package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating createRating(Rating rating);

    //get all
    List<Rating> getAllRatings();

    //get all by userID
    List<Rating> getRatingByUserId(String userId);

    //get all by hotels
    List<Rating> getRatingByHotelId(String hotelId);

    Rating deleteRating(String ratingId);
}
