package com.flightinportugal.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DataDTO {
    @SerializedName(value = "bags_price")
    private BagPriceDTO bagPrice;
    private double price;
}
