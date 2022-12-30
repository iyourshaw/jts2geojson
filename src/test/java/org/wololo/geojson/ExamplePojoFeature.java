package org.wololo.geojson;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamplePojoFeature extends BaseFeature<UUID, Point, ExamplePojoProperties> {

    @JsonCreator
    public ExamplePojoFeature(@JsonProperty("id") UUID id, @JsonProperty("geometry") Point geometry, @JsonProperty("properties") ExamplePojoProperties properties) {
        super(id, geometry, properties);
    }

}
