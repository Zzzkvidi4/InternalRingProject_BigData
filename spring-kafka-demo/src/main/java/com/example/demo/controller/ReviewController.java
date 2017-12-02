package com.example.demo.controller;

import com.example.demo.domain.Review;
import com.example.demo.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class ReviewController {

    private Sender sender;

    @PostMapping(value = "/send")
    public ResponseEntity sendData(@RequestBody Review demo) {
        sender.send(demo);
        return ok().body("created");
    }

    @GetMapping(value = "/")
    public ResponseEntity getStatus(){
        return ok().body("Application is running");
    }


    @RequestMapping(value = "/review",method = RequestMethod.POST)
    public String testSend(@RequestParam("hotel") int hotel, @RequestParam("text") String text,@RequestParam("distance") int distance,
                           @RequestParam("service") int service,@RequestParam("comfort") int comfort,
                           @RequestParam("price") int price){
        Review review =new Review();
        review.setHotel_id(hotel);
        review.setMessage(text);
        review.setComfort(comfort);
        review.setPrice(price);
        review.setService(service);
        review.setDistance_from_airport(distance);
        sender.send(review);
        return "redirect:/review";
    }

    @RequestMapping(value = "/review",method = RequestMethod.GET)
    public String test(){
        return "sendPage";
    }
    
     @RequestMapping(value = "/hotel",method = RequestMethod.POST)
    public String addHotel(@RequestParam("description") String description,@RequestParam("city") String city,
                           @RequestParam("id") int id,@RequestParam("name") String name,
                           @RequestParam("url") String url,@RequestParam("country") String country,
                           @RequestParam("pic") String pic){
        Hotel hotel=new Hotel();
        hotel.setAverage_score(0.0F);
        hotel.setCity(city);
        hotel.setCountry(country);
        hotel.setDescription(description);
        hotel.setIcon_url(pic);
        hotel.setId(id);
        hotel.setName(name);
        hotel.setLink(url);
        sender.sendHotel(hotel);
        return "redirect:/addHotel";
    }

    @RequestMapping(value = "/hotel",method = RequestMethod.GET)
    public String hotel(){
        return "addHotel";
    }

    @Autowired
    public ReviewController(Sender sender) {
        this.sender = sender;
    }
}
