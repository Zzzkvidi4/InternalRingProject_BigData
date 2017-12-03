package com.example.demo.controller;

import com.example.demo.domain.Hotel;
import com.example.demo.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping("/hotels")
public class HotelsListController {
    HotelService hotelService = new HotelService();

    @RequestMapping(method = RequestMethod.GET)
    public String getHotelsList(Model model){
        ArrayList<Hotel> hotels = hotelService.getHotels();
        if (hotels != null) {
            model.addAttribute("hotels", hotels);
        }
        return "hotels";
    }
}
