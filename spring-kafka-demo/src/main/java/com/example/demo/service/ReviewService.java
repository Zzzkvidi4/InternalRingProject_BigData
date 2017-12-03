package com.example.demo.service;

import com.example.demo.dao.ReviewRepository;
import com.example.demo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private KafkaSender kafkaSender;
    private ReviewRepository reviewRepository;

    public void save(Review review) {
        kafkaSender.send(review);
    }

    public List<Review> getReviewsByHotelId(Long id) {
        return reviewRepository.findByHotelId(id);
    }

    @Autowired
    public ReviewService(KafkaSender kafkaSender, ReviewRepository reviewRepository) {
        this.kafkaSender = kafkaSender;
        this.reviewRepository = reviewRepository;
    }
}
