package com.example.demo.controller;

import com.example.demo.domain.Hotel;
import com.example.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HotelController {
    private HotelService hotelService;

    @RequestMapping(value = "/hotel", method = RequestMethod.GET)
    public String hotel() {
        return "hotel";
    }

    @RequestMapping(value = "/hotel", method = RequestMethod.POST)
    public String saveHotel(@ModelAttribute("hotel") Hotel hotel) {
        hotelService.save(hotel);
        return "redirect:/hotel";
    }

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
}
