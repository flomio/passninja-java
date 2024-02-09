package com.passninja.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PassPayload {

    @JsonProperty private final String encrypted;
    @JsonProperty private final String decrypted;
    @JsonProperty private final String timeOfScan;

    @JsonCreator
    public PassPayload(
            @JsonProperty("encrypted") final String encrypted,
            @JsonProperty("decrypted") final String decrypted,
            @JsonProperty("timeOfScan") final String timeOfScan) {
        this.encrypted = encrypted;
        this.decrypted = decrypted;
        this.timeOfScan = timeOfScan;
    }

    public String getDecrypted() {
        return decrypted;
    }
}
