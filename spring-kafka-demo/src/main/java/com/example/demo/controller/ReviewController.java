package com.example.demo.controller;

import com.example.demo.domain.Review;
import com.example.demo.service.HotelService;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/hotels/{hotelId}/review")
public class ReviewController {
    private ReviewService reviewService;
    private HotelService hotelService;

    @GetMapping
    public String addReviewPage(@PathVariable("hotelId") Long hotelId, Model model) {
        model.addAttribute("hotel", hotelService.getHotelById(hotelId));
        return "review";
    }

    @PostMapping("/")
    public String saveReview(@PathVariable("hotelId") Long hotelId, @ModelAttribute("review") Review review) {
        reviewService.save(review);
        return "redirect:/hotels/" + hotelId;
    }


    @Autowired
    public ReviewController(ReviewService reviewService, HotelService hotelService) {
        this.reviewService = reviewService;
        this.hotelService = hotelService;
    }
}
