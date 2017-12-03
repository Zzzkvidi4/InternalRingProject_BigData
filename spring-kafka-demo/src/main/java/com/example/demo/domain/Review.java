package com.example.demo.domain;

public class Review {
    private String message;
    private Integer service;
    private Integer comfort;
    private Integer price;
    private Integer distanceFromAirport;
    private Integer hotelId;
    private String city;
    private String country;

    public Integer getDistanceFromAirport() {
        return distanceFromAirport;
    }

    public void setDistanceFromAirport(Integer distanceFromAirport) {
        this.distanceFromAirport = distanceFromAirport;
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

    public void setHotelId(int hotelId){
        this.hotelId = hotelId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "{\"message\":\"" + message + '\"' +
                ", \"service\":" + service +
                ", \"comfort\":" + comfort +
                ", \"price\":" + price +
                ", \"distance_from_airport\"=" + distanceFromAirport +
                ", \"hotel_id\":" + hotelId + "\"}";
    }
}
