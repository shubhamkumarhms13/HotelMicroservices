package com.lcwd.hotel.services;

import com.lcwd.hotel.HotelServiceApplication;
import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel createHotel(Hotel hotel);

    //getall
    List<Hotel> getAllHotel();

    //single get
    Hotel getHotel(String id);

    Hotel deleteUser(String id);
}
