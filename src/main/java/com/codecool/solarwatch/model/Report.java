package com.codecool.solarwatch.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Report(String city, String sunRise, String sunSet, LocalDate date){}
