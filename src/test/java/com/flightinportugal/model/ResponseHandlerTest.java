package com.flightinportugal.model;

import com.flightinportugal.api.ApiURL;
import com.flightinportugal.dto.BagPriceDTO;
import com.flightinportugal.dto.DataDTO;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResponseHandlerTest {

    @Test
    void getPriceAverageTickets() {
        DataDTO flight1 = new DataDTO();
        flight1.setPrice(40);

        DataDTO flight2 = new DataDTO();
        flight2.setPrice(60);

        DataDTO flight3 = new DataDTO();
        flight3.setPrice(55);

        ResponseHandler responseHandler = new ResponseHandler(Arrays.asList(flight1, flight2, flight3));
        Assertions.assertThat(responseHandler.getPriceAverageTickets()).isEqualTo(51.66, Offset.offset(2.0));
    }

    @Test
    void getPriceAverageForBaggages() {
        DataDTO flight1 = new DataDTO();
        BagPriceDTO bagPriceFlight1 = new BagPriceDTO();
        bagPriceFlight1.setPriceForOneBaggage(10);
        bagPriceFlight1.setPriceForTwoBaggage(17);
        flight1.setBagPrice(bagPriceFlight1);
        flight1.setPrice(40);

        DataDTO flight2 = new DataDTO();
        BagPriceDTO bagPriceFlight2 = new BagPriceDTO();
        bagPriceFlight2.setPriceForOneBaggage(15);
        bagPriceFlight2.setPriceForTwoBaggage(12);
        flight2.setBagPrice(bagPriceFlight2);
        flight2.setPrice(60);

        DataDTO flight3 = new DataDTO();
        BagPriceDTO bagPriceFlight3 = new BagPriceDTO();
        bagPriceFlight3.setPriceForOneBaggage(11);
        bagPriceFlight3.setPriceForTwoBaggage(15);
        flight3.setBagPrice(bagPriceFlight2);
        flight3.setPrice(55);

        ResponseHandler responseHandler = new ResponseHandler(Arrays.asList(flight1, flight2, flight3));
        Assertions.assertThat(responseHandler.getPriceAverageTickets()).isEqualTo(51.66, Offset.offset(2.0));
        Assertions.assertThat(responseHandler.getPriceAverageForOneBagagge()).isEqualTo(12, Offset.offset(2.0));
        Assertions.assertThat(responseHandler.getPriceAverageForTwoBagagges()).isEqualTo(14.66, Offset.offset(2.0));
    }

    @Test
    void buildResponse() {
        BagsAverage bagsAverageMock = mock(BagsAverage.class);
        when(bagsAverageMock.getOneBaggage()).thenReturn(12.0);
        when(bagsAverageMock.getTwoBaggage()).thenReturn(17.0);

        FlightAverage flightAverageMock = mock(FlightAverage.class);
        when(flightAverageMock.getBagsAverage()).thenReturn(bagsAverageMock);

        ApiURL apiURL = new ApiURL.Builder("OPO", "LIS", "01/01/2020").build();

        DataDTO flight1 = new DataDTO();
        BagPriceDTO bagPriceFlight1 = new BagPriceDTO();
        bagPriceFlight1.setPriceForOneBaggage(10);
        bagPriceFlight1.setPriceForTwoBaggage(17);
        flight1.setBagPrice(bagPriceFlight1);
        flight1.setPrice(40);

        ResponseHandler responseHandler = new ResponseHandler(Collections.singletonList(flight1));
        String expectedUrl = "{\"priceAverage\":\"40\",\"bagsAverage\":{\"oneBaggage\":\"10\",\"twoBaggage\":\"17\"},\"dateFrom\":\"01/01/2020\",\"sourceAirport\":\"OPO\",\"targetAirport\":\"LIS\",\"partner\":\"TAP\",\"currency\":\"EUR\"}";
        Assertions.assertThat(expectedUrl).isEqualTo(responseHandler.buildResponse(apiURL));
    }
}