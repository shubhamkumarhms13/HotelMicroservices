package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;
import org.springframework.stereotype.Service;

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
}
