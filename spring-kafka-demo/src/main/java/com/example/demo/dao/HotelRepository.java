package com.example.demo.dao;

import com.example.demo.domain.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelRepository extends MongoRepository<Hotel, Long> {
    Hotel findOneById(Long id);
}