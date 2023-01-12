package org.wololo.geojson.examples;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.wololo.geojson.BaseFeature;
import org.wololo.geojson.Point;

/**
 * Example feature class with an immutable properties POJO, Point geometry, and UUID as ID.
 */
public class ExampleImmutablePojoFeature extends BaseFeature<UUID, Point, ExampleImmutablePojoProperties> {
    
        @JsonCreator
        public ExampleImmutablePojoFeature(
                @JsonProperty("id") UUID id, 
                @JsonProperty("geometry") Point geometry, 
                @JsonProperty("properties") ExampleImmutablePojoProperties properties) {
            super(id, geometry, properties);
        }
}
