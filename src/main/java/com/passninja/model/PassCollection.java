package com.passninja.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PassCollection extends PassninjaCollection<Pass> {

    @JsonCreator
    public PassCollection(
            @JsonProperty("data") final List<Pass> data,
            @JsonProperty("count") final int count,
            @JsonProperty("object") final String object) {
        super(data, count, object);
    }

}
