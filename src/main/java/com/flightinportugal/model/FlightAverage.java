package com.flightinportugal.model;

import lombok.Data;

@Data
public class FlightAverage {
    private double priceAverage;
    private BagsAverage bagsAverage;
    private String dateFrom;
    private String dateTo;
    private String sourceAirport;
    private String targetAirport;
    private String partner;
    private String currency;
}
