package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.Report;
import com.codecool.solarwatch.model.SunriseSunset;
import com.codecool.solarwatch.repository.SunriseSunsetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class SunriseSunsetService {
    private static final Logger logger = LoggerFactory.getLogger(GeoCodeService.class);
    //private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final JSONExtractor extractor;
    private final CityService cityService;
    private final SunriseSunsetRepository sunriseSunsetRepository;

    public SunriseSunsetService(WebClient webClient, JSONExtractor extractor, CityService cityService, SunriseSunsetRepository sunriseSunsetRepository) {
        this.webClient = webClient;
        this.extractor = extractor;
        this.cityService = cityService;
        this.sunriseSunsetRepository = sunriseSunsetRepository;
    }

    public Report getReport(String cityName, LocalDate date) {
        City city = cityService.getCityByName(cityName);
        Optional<SunriseSunset> optionalSS = sunriseSunsetRepository.findByCityAndDate(city, date);
        if (optionalSS.isPresent()) {
            return makeReport(optionalSS.get());
        }
        SunriseSunset newData = new SunriseSunset();
        String[] fetchedData = fetchData(city, date);
        newData.setCity(city);
        newData.setDate(date);
        newData.setSunrise(fetchedData[0]);
        newData.setSunset(fetchedData[1]);

        sunriseSunsetRepository.save(newData);
        return makeReport(newData);

    }

    private Report makeReport(SunriseSunset sunRiseSet) {
        return new Report(sunRiseSet.getCity().getName(), sunRiseSet.getSunrise(), sunRiseSet.getSunset(), sunRiseSet.getDate());
    }

    public String[] fetchData(City city, LocalDate date) {
        String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s", city.getLatitude(), city.getLongitude(), date);

String response = webClient
        .get() // request type
        .uri(url) // request URI
        .retrieve() // sends the actual request
        .bodyToMono(String.class) // parses the response
        .block(); // waits for the response

        //String result = restTemplate.getForObject(url, String.class);
        String[] returnValue = new String[2];
        returnValue[0] = extractor.extractStringValueFromSunriseSunsetApi(response, "sunrise");
        returnValue[1] = extractor.extractStringValueFromSunriseSunsetApi(response, "sunset");
        return returnValue;
    }
}
