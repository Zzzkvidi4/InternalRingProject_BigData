package com.example.demo.controller;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Review;
import com.example.demo.service.HotelService;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {
    private HotelService hotelService;
    private ReviewService reviewService;

    @GetMapping("/")
    public String addHotelPage() {
        return "addHotel";
    }

    @GetMapping("/{hotelId}")
    public String getHotelInfo(@PathVariable("hotelId") Long hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        List<Review> reviews = reviewService.getReviewsByHotelId(hotelId);
        model.addAttribute("hotel", hotel);
        model.addAttribute("reviews", reviews);
        return "hotel_info";
    }

    @PostMapping("/")
    public String saveHotel(@ModelAttribute("hotel") Hotel hotel) {
        hotelService.save(hotel);
        return "redirect:/hotels";
    }

    @Autowired
    public HotelController(HotelService hotelService, ReviewService reviewService) {
        this.hotelService = hotelService;
        this.reviewService = reviewService;
    }
}
