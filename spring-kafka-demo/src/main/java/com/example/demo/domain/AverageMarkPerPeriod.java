package com.example.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="averageMarks")
public class AverageMarkPerPeriod {
    @Id
    private String id;
    private Double averageMark;
    private Long hotelId;
    private Date date;
    private Integer version;

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setAverageMark(Double averageMark) {
        this.averageMark = averageMark;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Double getAverageMark() {
        return averageMark;
    }

    public Integer getVersion() {
        return version;
    }

    public Long getHotelId() {
        return hotelId;
    }
}
