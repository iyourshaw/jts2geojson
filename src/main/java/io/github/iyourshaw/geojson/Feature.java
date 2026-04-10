package io.github.iyourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * GeoJSON Feature with an untyped id, a {@link Geometry}, and a
 * map-backed {@link Properties} object.
 */
@JsonPropertyOrder({"type", "id", "geometry", "properties"})
public class Feature extends BaseFeature<Object, Geometry, Properties> {

    /**
     * Creates a feature with no id.
     * @param geometry the feature geometry
     * @param properties the feature properties
     */
    public Feature(
            @JsonProperty("geometry") Geometry geometry,
            @JsonProperty("properties") Properties properties) {
        super(geometry, properties);
    }

    /**
     * Creates a feature with an id.
     * @param id the feature id
     * @param geometry the feature geometry
     * @param properties the feature properties
     */
    @JsonCreator
    public Feature(
            @JsonProperty("id") Object id,
            @JsonProperty("geometry") Geometry geometry,
            @JsonProperty("properties") Properties properties) {
        super(id, geometry, properties);
    }

 
}
