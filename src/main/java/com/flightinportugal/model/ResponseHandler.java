package com.flightinportugal.model;

import com.flightinportugal.api.ApiURL;
import com.flightinportugal.dto.DataDTO;
import com.flightinportugal.util.DoubleSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ResponseHandler {
    private List<DataDTO> data;

    public ResponseHandler(final List<DataDTO> data) {
        this.data = data;
    }

    public double getPriceAverageTickets() {
        return data.stream()
                .mapToDouble(DataDTO::getPrice)
                .average()
                .orElse(Double.NaN);
    }

    public double getPriceAverageForOneBagagge() {
        return data.stream()
                .mapToDouble(d -> d.getBagPrice().getPriceForOneBaggage())
                .average()
                .orElse(Double.NaN);
    }

    public double getPriceAverageForTwoBagagges() {
        return data.stream()
                .mapToDouble(d -> d.getBagPrice().getPriceForTwoBaggage())
                .average()
                .orElse(Double.NaN);
    }

    public String buildResponse(ApiURL apiURL) {
        BagsAverage bagsAverage = new BagsAverage(getPriceAverageForOneBagagge(), getPriceAverageForTwoBagagges());
        FlightAverage flightAverage = new FlightAverage();
        flightAverage.setBagsAverage(bagsAverage);
        flightAverage.setDateFrom(apiURL.getDateFrom());
        flightAverage.setDateTo(apiURL.getDateTo());
        flightAverage.setPartner(apiURL.getPartner());
        flightAverage.setPriceAverage(getPriceAverageTickets());
        flightAverage.setSourceAirport(apiURL.getFlyFrom());
        flightAverage.setTargetAirport(apiURL.getFlyTo());
        flightAverage.setCurrency(apiURL.getCurrency());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class, new DoubleSerializer());
        Gson gson = gsonBuilder.create();

        return gson.toJson(flightAverage);
    }
}
