package com.example.demo.service;

import com.example.demo.dao.HotelRepository;
import com.example.demo.domain.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private static final String COLLECTION_NAME = "hotels";
    private HotelRepository hotelRepository;
    private CounterService counterService;

    public void save(Hotel hotel) {
        hotel.setId(counterService.getNextSequence(COLLECTION_NAME));
        hotelRepository.save(hotel);
    }

    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findOneById(id);
    }

    @Autowired
    public HotelService(HotelRepository hotelRepository, CounterService counterService) {
        this.hotelRepository = hotelRepository;
        this.counterService = counterService;
    }
}
