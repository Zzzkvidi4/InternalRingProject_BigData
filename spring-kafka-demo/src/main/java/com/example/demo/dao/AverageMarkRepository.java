package com.example.demo.dao;

import com.example.demo.domain.AverageMarkPerPeriod;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AverageMarkRepository extends MongoRepository<AverageMarkPerPeriod, Long> {
    List<AverageMarkPerPeriod> findTop10ByHotelIdOrderByDateDesc(Long hotelId);
}
