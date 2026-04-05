package net.yourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
public class LineString extends Geometry {
    private final double[][] coordinates;
    private final double[] bbox;

    public LineString(@JsonProperty("coordinates") double [][] coordinates) {
        super();
        this.coordinates = coordinates;
        this.bbox = null;
    }

    @JsonCreator
    public LineString(@JsonProperty("coordinates") double [][] coordinates,
            @JsonProperty(value = "bbox", isRequired = OptBoolean.FALSE) double[] bbox) {
        super();
        this.coordinates = coordinates;
        this.bbox = bbox;
    }

}
