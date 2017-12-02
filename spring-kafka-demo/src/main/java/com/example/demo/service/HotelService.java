package com.example.demo.service;

import com.example.demo.domain.Hotel;
import com.mongodb.*;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
public class HotelService {

    public void save(Hotel hotel){
        MongoClient mongoClient= null;
        try {
            mongoClient = new MongoClient();
            DB database=mongoClient.getDB("BigData");
            DBObject hotelObject=new BasicDBObject("id",hotel.getId()).append("name",hotel.getName())
                    .append("country",hotel.getCountry()).append("city",hotel.getCity())
                    .append("description",hotel.getDescription()).append("average_score",hotel.getAverageScore())
                    .append("link",hotel.getLink());
            DBCollection collection=database.getCollection("Hotels");
            collection.insert(hotelObject);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }        
    }

}
