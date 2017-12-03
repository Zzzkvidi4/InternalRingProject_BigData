package com.example.demo.controller;

import com.example.demo.domain.Hotel;
import com.example.demo.domain.Review;
import com.example.demo.service.HotelService;
import com.example.demo.service.KafkaSender;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class HotelController {
    private HotelService hotelService;

    @RequestMapping(value = "/hotel", method = RequestMethod.GET)
    public String hotel() {
        return "hotel";
    }

    @RequestMapping(value = "/hotel/{hotel_id}", method = RequestMethod.GET)
    public String hotel(@PathVariable int hotel_id, Model model) {
        Hotel hotel = hotelService.getHotelById(hotel_id);
        ArrayList<Review> reviews = ReviewService.getReviewsByHotelId(hotel_id);
        model.addAttribute("hotel", hotel);
        model.addAttribute("reviews", reviews);
        return "hotel_info";
    }

    @RequestMapping(value = "/hotel", method = RequestMethod.POST)
    public String saveHotel(@ModelAttribute("hotel") Hotel hotel) {
        hotelService.save(hotel);
        return "hotels";
    }

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
}
