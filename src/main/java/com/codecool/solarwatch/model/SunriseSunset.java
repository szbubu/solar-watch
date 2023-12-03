package com.codecool.solarwatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class SunriseSunset {
private LocalDate date;

private String sunrise;

private String sunset;
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private City city;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
