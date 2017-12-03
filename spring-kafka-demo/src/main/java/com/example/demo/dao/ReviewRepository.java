package com.example.demo.dao;

import com.example.demo.domain.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, Long> {
    List<Review> findByHotelId(Long hotelId);
}
