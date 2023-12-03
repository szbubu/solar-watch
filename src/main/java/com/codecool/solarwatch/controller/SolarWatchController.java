package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.Coordinates;
import com.codecool.solarwatch.model.Report;
import com.codecool.solarwatch.service.GeoCodeService;
import com.codecool.solarwatch.service.SunriseSunsetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;


@RestController
public class SolarWatchController {
    private final GeoCodeService geoCodeService;
    private final SunriseSunsetService sunriseSunsetService;

    public SolarWatchController(GeoCodeService geoCodeService, SunriseSunsetService sunriseSunsetService) {
        this.geoCodeService = geoCodeService;
        this.sunriseSunsetService = sunriseSunsetService;
    }

    @GetMapping("/data")
    public ResponseEntity<?> getReport(@RequestParam(required = false) LocalDate date, @RequestParam(required = false) String city) {
        if (date == null) {
            date = LocalDate.now();
        }
        if (city==null){
            city="Budapest";
        }

        Report report = sunriseSunsetService.getReport(city, date);
        return ResponseEntity.ok(report);

    }
}
