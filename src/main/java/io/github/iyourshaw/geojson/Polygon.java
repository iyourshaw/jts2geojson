package io.github.iyourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Getter;

/**
 * GeoJSON Polygon geometry. Coordinates are an array of rings;
 * the first is the exterior ring, any further rings are holes.
 */
@Getter
@JsonInclude(Include.NON_NULL)
public class Polygon extends Geometry {
    private final double[][][] coordinates;
    private final double[] bbox;

    /**
     * Creates a Polygon with the given coordinate rings.
     * @param coordinates array of rings; first is exterior, rest are holes
     */
    public Polygon(@JsonProperty("coordinates") double [][][] coordinates) {
        super();
        this.coordinates = coordinates;
        this.bbox = null;
    }

    /**
     * Creates a Polygon with the given coordinate rings and bounding box.
     * @param coordinates array of rings; first is exterior, rest are holes
     * @param bbox optional bounding box, or {@code null}
     */
    @JsonCreator
    public Polygon(@JsonProperty("coordinates") double [][][] coordinates,
                   @JsonProperty(value = "bbox", isRequired = OptBoolean.FALSE) double[] bbox) {
        super();
        this.coordinates = coordinates;
        this.bbox = bbox;
    }

}
