package com.example.demo.service;

import com.example.demo.dao.HotelRepository;
import com.example.demo.domain.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private HotelRepository hotelRepository;

    public void save(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findOneById(id);
    }

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
}
