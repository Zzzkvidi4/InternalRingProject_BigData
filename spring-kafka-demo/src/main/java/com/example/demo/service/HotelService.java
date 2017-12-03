package com.example.demo.service;

import com.example.demo.dao.HotelRepository;
import com.example.demo.domain.Hotel;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

    public void save(Hotel hotel) {
        hotelRepository.save(hotel);
    }

}
