package com.example.demo.service;

import com.example.demo.domain.Hotel;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;

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
            DBCollection collection=database.getCollection("hotels");
            collection.insert(hotelObject);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }        
    }

    public ArrayList<Hotel> getHotels(){
        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("BigData");
            ArrayList<Hotel> hotels = new ArrayList<>();
            for (DBObject dbHotel: db.getCollection("hotels").find()){
                hotels.add(Hotel.convert(dbHotel));
            }
            return hotels;
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
        return null;
    }

    public Hotel getHotelById(int id){
        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("BigData");
            DBCollection hotels = db.getCollection("hotels");
            DBObject dbHotel = hotels.find(new BasicDBObject("id", id)).one();
            return Hotel.convert(dbHotel);
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
        return null;
    }
}
