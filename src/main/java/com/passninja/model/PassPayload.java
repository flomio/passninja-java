package com.passninja.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PassPayload {
    
    @JsonProperty private final String encrypted;
    @JsonProperty private final String decrypted;
    @JsonProperty private final String time_of_scan;

    @JsonCreator
    public PassPayload(
        @JsonProperty("encrypted") final String encrypted,
        @JsonProperty("decrypted") final String decrypted,
        @JsonProperty("time_of_scan") final String time_of_scan) {
        this.encrypted = encrypted;
        this.decrypted = decrypted;
        this.time_of_scan = time_of_scan;
    }
}