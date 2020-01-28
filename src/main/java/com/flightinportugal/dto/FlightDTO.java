package com.flightinportugal.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class FlightDTO {
    private String time;
    @SerializedName(value = "data")
    private List<DataDTO> data;
}
