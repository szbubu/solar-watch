package com.codecool.solarwatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JSONExtractor /*implements Extractor*/ {

    public double extractLongValue(String jsonResponse, String key) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(jsonResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rootNode);
        JsonNode firstNode = rootNode.get(0);
        JsonNode desiredNode = firstNode.get(key);

        if (desiredNode != null) {
            return desiredNode.asDouble();
        } else {
            return 0;
        }
    }

    public String extractStringValueFromSunriseSunsetApi(String jsonResponse, String key) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(jsonResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode resultsNode = rootNode.get("results");
        JsonNode desiredNode = resultsNode.get(key);
        if (desiredNode != null) {
            return desiredNode.asText();
        } else {
            return "";
        }
    }
    public String extractStringFromGEOApi(String jsonResponse, String key) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(jsonResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rootNode);
        JsonNode firstNode = rootNode.get(0);
        JsonNode desiredNode = firstNode.get(key);

        if (desiredNode != null) {
            return desiredNode.asText();
        } else {
            return "";

        }
    }
}
