package com.iymaps.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Getter;

/** GeoJSON MultiLineString geometry. Coordinates are an array of line strings. */
@Getter
@JsonInclude(Include.NON_NULL)
public class MultiLineString extends Geometry {
    private final double[][][] coordinates;
    private final double[] bbox;

    /**
     * @param coordinates array of line strings
     */
    public MultiLineString(@JsonProperty("coordinates") double [][][] coordinates) {
        super();
        this.coordinates = coordinates;
        this.bbox = null;
    }

    /**
     * @param coordinates array of line strings
     * @param bbox optional bounding box, or {@code null}
     */
    @JsonCreator
    public MultiLineString(@JsonProperty("coordinates") double [][][] coordinates,
                           @JsonProperty(value = "bbox", isRequired = OptBoolean.FALSE) double[] bbox) {
        super();
        this.coordinates = coordinates;
        this.bbox = bbox;
    }

}
