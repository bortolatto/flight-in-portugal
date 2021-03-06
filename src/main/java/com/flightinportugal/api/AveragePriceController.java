package com.flightinportugal.api;

import com.flightinportugal.dto.FlightDTO;
import com.flightinportugal.model.ResponseHandler;
import com.flightinportugal.service.RequestEntityService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping(path = "/flight/", produces = "application/json")
@Validated
public class AveragePriceController {
    private static final Logger logger = LoggerFactory.getLogger(AveragePriceController.class);
    @Autowired
    private RequestEntityService service;

    @GetMapping("/avg")
    @Cacheable("avg")
    public String getAverage(@RequestParam @Pattern(regexp = "RYR|TAP") String partner,
                      @Valid @RequestParam(name = "date_from") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
                      @RequestParam(name = "date_to", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
                      @RequestParam(name = "source_airport") @Pattern(regexp = "LIS|OPO") String sourceAirport,
                      @RequestParam(name = "target_airport") @Pattern(regexp = "LIS|OPO") String targetAirport) {
        if (sourceAirport.equals(targetAirport)) {
            throw new IllegalArgumentException("Source and target airports should not be equals.");
        }

        RestTemplate restTemplate = new RestTemplate();
        ApiURL apiURL = new ApiURL.Builder(sourceAirport, targetAirport, dateFrom.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .partner(partner)
                .dateTo(dateTo)
                .build();

        ResponseEntity<String> response = doRestCall(restTemplate, apiURL.getUrl());
        FlightDTO dto = new Gson().fromJson(response.getBody(), FlightDTO.class);
        ResponseHandler responseHandler = new ResponseHandler(new ArrayList<>(dto.getData()));

        service.saveNewRequest(apiURL.getUrl());
        return responseHandler.buildResponse(apiURL);
    }

    private ResponseEntity<String> doRestCall(RestTemplate restTemplate, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        logger.info("CALLING THE SKY PICKER REST API...");
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

}
