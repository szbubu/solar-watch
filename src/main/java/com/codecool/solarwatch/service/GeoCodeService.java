package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class GeoCodeService {

    private static final Logger logger = LoggerFactory.getLogger(GeoCodeService.class);


    //private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final JSONExtractor extractor;
    private static final String API_KEY = "78d8ed08d780d8790c616dfa99342aaa";

    public GeoCodeService(WebClient webClient, JSONExtractor extractor) {
        this.webClient = webClient;
        this.extractor = extractor;
    }

    public Optional<City> getCity(String location) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", location, API_KEY);

        //String result = restTemplate.getForObject(url, String.class);
        String result = webClient
                .get() // request type
                .uri(url) // request URI
                .retrieve() // sends the actual request
                .bodyToMono(String.class) // parses the response
                .block(); // waits for the response

        if (result == null || result.length() < 4) {
            return Optional.empty();
        }

        City foundCity = new City();
        foundCity.setName(location);
        foundCity.setLatitude(extractor.extractLongValue(result, "lat"));
        foundCity.setLongitude(extractor.extractLongValue(result, "lon"));
        foundCity.setState(extractor.extractStringFromGEOApi(result, "state"));
        foundCity.setCountry(extractor.extractStringFromGEOApi(result, "country"));
        return Optional.of(foundCity);
    }



}
