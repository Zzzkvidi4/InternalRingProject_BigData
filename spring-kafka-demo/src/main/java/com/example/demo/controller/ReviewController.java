package com.example.demo.controller;

import com.example.demo.domain.Review;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class ReviewController {
    private ReviewService reviewService;

    @GetMapping(value = "/")
    public ResponseEntity getStatus() {
        return ok().body("Application is running");
    }


    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public String saveReview(@ModelAttribute("review") Review review) {
        reviewService.save(review);
        return "redirect:/hotel/" + review.getHotelId();
    }

    @RequestMapping(value = "/review/{hotel_id}", method = RequestMethod.GET)
    public String review(@PathVariable("hotel_id") int hotel_id, Model model) {
        model.addAttribute("hotel_id", hotel_id);
        return "review";
    }


    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}
