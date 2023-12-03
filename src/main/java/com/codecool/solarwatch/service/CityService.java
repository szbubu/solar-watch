package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final GeoCodeService geoCodeService;

    public CityService(CityRepository cityRepository, GeoCodeService geoCodeService) {
        this.cityRepository = cityRepository;
        this.geoCodeService = geoCodeService;
    }

    public City getCityByName(String cityName) {
        Optional<City> optionalCity = cityRepository.findByName(cityName);
        if (optionalCity.isPresent()) {
            return optionalCity.get();
        }
       optionalCity = geoCodeService.getCity(cityName);
        if (optionalCity.isPresent()) {
            cityRepository.save(optionalCity.get());
            return optionalCity.get();
        }

        return null;
    }

}
