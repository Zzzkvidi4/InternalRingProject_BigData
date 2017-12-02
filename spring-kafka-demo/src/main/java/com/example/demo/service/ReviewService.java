package com.example.demo.service;

import com.example.demo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private KafkaSender kafkaSender;

    public void save(Review review) {
        kafkaSender.send(review);
    }

    @Autowired
    public ReviewService(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }
}
