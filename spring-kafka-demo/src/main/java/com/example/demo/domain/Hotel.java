package com.example.demo.domain;

import com.mongodb.DBObject;

public class Hotel {
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String description;
    private Float averageScore;
    private String link;
    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Float averageScore) {
        this.averageScore = averageScore;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '\"' +
                ", \"country\":\"" + country + '\"' +
                ", \"city\":\"" + city + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"averageScore\":" + averageScore +
                ", \"link\":\"" + link + '\"' +
                ", \"icon\":\"" + icon + "\"}";
    }

    public static Hotel convert(DBObject dbHotel){
        Hotel hotel = new Hotel();
        hotel.setId((int)dbHotel.get("id"));
        hotel.setName((String)dbHotel.get("name"));
        hotel.setCountry((String)dbHotel.get("country"));
        hotel.setCity((String)dbHotel.get("city"));
        hotel.setLink((String)dbHotel.get("link"));
        hotel.setDescription((String)dbHotel.get("description"));
        return hotel;
    }
}
