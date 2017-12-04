package com.example.demo.service;

import com.example.demo.dao.AverageMarkRepository;
import com.example.demo.domain.AverageMarkPerPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AverageMarkPerPeriodService {
    private MongoOperations mongo;
    private AverageMarkRepository averageMarkRepository;

    @Autowired
    public AverageMarkPerPeriodService(MongoOperations mongo, AverageMarkRepository averageMarkRepository){
        this.mongo = mongo;
        this.averageMarkRepository = averageMarkRepository;
    }

    public List<AverageMarkPerPeriod> getAverageMarksByHotelId(Long hotelId){
        return averageMarkRepository.findTop10ByHotelIdOrderByDateDesc(hotelId);
    }
}
