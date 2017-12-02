package com.example.demo.domain;

public class Review {
    private String message;
    private Integer service;
    private Integer comfort;
    private Integer price;
    private Integer distance_from_airport;
    private Integer hotel_id;

    public Integer getDistance_from_airport() {
        return distance_from_airport;
    }

    public void setDistance_from_airport(Integer distance_from_airport) {
        this.distance_from_airport = distance_from_airport;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public Integer getComfort() {
        return comfort;
    }

    public void setComfort(Integer comfort) {
        this.comfort = comfort;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setHotel_id(int hotel_id){
        this.hotel_id = hotel_id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"hotel_id\":" + hotel_id +
                ", \"message\":\"" + message +
                "\", \"service\":" + service +
                ", \"comfort\":" + comfort +
                ", \"price\":" + price +
                ", \"distance_from_airport\":" + distance_from_airport +
                "}";
    }
}
