package com.flightinportugal.api;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApiURL {
    private static final String BASE_URL = "https://api.skypicker.com/flights?";
    @Getter private String url;
    @Getter private String partner;
    @Getter private String dateFrom;
    @Getter private String dateTo;
    @Getter private String flyFrom;
    @Getter private String flyTo;
    @Getter private String currency;

    private ApiURL(Builder builder) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(BASE_URL);
        urlBuilder.append("partner=");
        urlBuilder.append(builder.partner);
        urlBuilder.append("&date_from=");
        urlBuilder.append(builder.dateFrom);
        if (!StringUtils.isEmpty(builder.dateTo)) {
            urlBuilder.append("&date_to=");
            urlBuilder.append(builder.dateTo);
        }
        urlBuilder.append("&fly_from=");
        urlBuilder.append(builder.flyFrom);
        urlBuilder.append("&fly_to=");
        urlBuilder.append(builder.flyTo);
        urlBuilder.append("&curr=");
        urlBuilder.append(builder.currency);
        this.url = urlBuilder.toString();
        this.partner = builder.partner;
        this.dateFrom = builder.dateFrom;
        this.dateTo = builder.dateTo;
        this.flyFrom = builder.flyFrom;
        this.flyTo = builder.flyTo;
        this.currency = builder.currency;
    }

    public static class Builder {
        private final String flyFrom;
        private final String flyTo;
        private final String dateFrom;
        private String partner = "TAP";
        private String currency = "EUR";
        private String dateTo;

        public Builder(String flyFrom, String flyTo, String dateFrom) {
            this.flyFrom = flyFrom;
            this.flyTo = flyTo;
            this.dateFrom = dateFrom;
        }

        public Builder partner(String value) {
            partner = value;
            return this;
        }

        public Builder currency(String value) {
            currency = value;
            return this;
        }

        public Builder dateTo(LocalDate value) {
            dateTo = value == null ? "" : value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return this;
        }

        public ApiURL build() {
            return new ApiURL(this);
        }

    }
}
