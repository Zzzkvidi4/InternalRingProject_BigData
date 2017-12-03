package com.example.demo.service;

import com.example.demo.domain.Review;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;

@Service
public class ReviewService {
    private KafkaSender kafkaSender;

    public void save(Review review) {
        kafkaSender.send(review);
    }

    @Autowired
    public ReviewService(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    public static ArrayList<Review> getReviewsByHotelId(int id){
        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient();
            DB db = mongoClient.getDB("BigData");
            ArrayList<Review> reviews = new ArrayList<>();
            for (DBObject dbHotel: db.getCollection("reviews").find(new BasicDBObject("hotel_id", id))){
                reviews.add(Review.convert(dbHotel));
            }
            return reviews;
        } catch (UnknownHostException e){
            e.printStackTrace();
        }
        return null;
    }
}
