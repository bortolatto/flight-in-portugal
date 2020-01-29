package com.flightinportugal.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BagPriceDTO {
    @SerializedName(value = "1")
    private double priceForOneBaggage;
    @SerializedName(value = "2")
    private double priceForTwoBaggage;
}
