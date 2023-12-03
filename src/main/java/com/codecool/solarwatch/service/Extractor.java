package com.codecool.solarwatch.service;

public interface Extractor {
    public long extractLongValue(String jsonResponse, String key);
    public String extractStringValue(String jsonResponse, String key);
    String extractStringFromGEOApi(String jsonResponse, String key);
}
