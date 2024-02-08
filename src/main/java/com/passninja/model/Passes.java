package com.passninja.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Passes {

    @JsonProperty
    private final List<Pass> passList;

    @JsonCreator
    public Passes(@JsonProperty("passes") final List<Pass> passList) {
        this.passList = passList;
    }

    public List<Pass> getPasses() {
        return passList;
    }
}
