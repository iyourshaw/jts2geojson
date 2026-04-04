package net.yourshaw.geojson.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.yourshaw.geojson.BaseFeatureCollection;

public class ExamplePojoFeatureCollection extends BaseFeatureCollection<ExamplePojoFeature> {

    @JsonCreator
    public ExamplePojoFeatureCollection(@JsonProperty("features") ExamplePojoFeature[] features) {
        super(features);
    }
}
