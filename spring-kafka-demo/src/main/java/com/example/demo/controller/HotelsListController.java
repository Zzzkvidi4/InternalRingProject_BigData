package com.example.demo.controller;

import com.example.demo.domain.Hotel;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelsListController {
    private HotelService hotelService;

    @GetMapping
    public String getHotelsList(Model model) {
        List<Hotel> hotels = hotelService.getHotels();
        model.addAttribute("hotels", hotels);
        return "hotels";
    }

    @Autowired
    public HotelsListController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
}
