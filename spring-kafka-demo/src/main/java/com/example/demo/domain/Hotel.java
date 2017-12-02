package com.example.demo.domain;

public class Hotel {
    private Integer id;
    private String name;
    private String country;
    private String city;
    private String description;
    private Float average_score;
    private String link;
    private String icon_url;

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

    public Float getAverage_score() {
        return average_score;
    }

    public void setAverage_score(Float average_score) {
        this.average_score = average_score;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id +
                ",\"name\":\"" + name + "\"" +
                ",\"country\":\"" + country + "\"" +
                ",\"city\":\"" + city + "\"" +
                ",\"description\":\"" + description + "\"" +
                ",\"average_score\":" + average_score +
                ",\"link\":\"" + link + "\"" +
                ",\"icon_url\":\"" + icon_url + "\"" +
                '}';
    }
}
