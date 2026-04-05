package com.iymaps.geojson.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iymaps.geojson.BaseFeatureCollection;

public class ExamplePojoFeatureCollection extends BaseFeatureCollection<ExamplePojoFeature> {

    @JsonCreator
    public ExamplePojoFeatureCollection(@JsonProperty("features") ExamplePojoFeature[] features) {
        super(features);
    }
}
