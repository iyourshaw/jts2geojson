package net.yourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
public class Point extends Geometry {
    private final double[] coordinates;
    private final double[] bbox;

    public Point (double longitude, double latitude) {
        super();
        this.coordinates = new double[] {longitude, latitude};
        this.bbox = null;
    }

    public Point(@JsonProperty("coordinates") double[] coordinates) {
        super();
        this.coordinates = coordinates;
        this.bbox = null;
    }

    @JsonCreator
    public Point(@JsonProperty("coordinates") double[] coordinates,
                 @JsonProperty(value = "bbox", isRequired = OptBoolean.FALSE) double[] bbox) {
        super();
        this.coordinates = coordinates;
        this.bbox = bbox;
    }

}
