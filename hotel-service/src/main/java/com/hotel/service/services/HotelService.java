package com.hotel.service.services;

import com.hotel.service.entities.Hotel;

import java.util.List;

public interface HotelService {

    // create hotel
    Hotel saveHotel(Hotel hotel);

    // get all hotels
    List<Hotel> getAllHotels();

    // get single hotel by id
    Hotel getHotelById(String id);
}
