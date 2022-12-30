package org.wololo.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamplePojoFeatureCollection extends BaseFeatureCollection<ExamplePojoFeature> {

    @JsonCreator
    public ExamplePojoFeatureCollection(@JsonProperty("features") ExamplePojoFeature[] features) {
        super(features);
    }
}
