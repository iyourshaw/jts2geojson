package net.yourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonInclude(Include.NON_NULL)
public class MultiPoint extends Geometry {
    private final double[][] coordinates;
    private final double[] bbox;

    public MultiPoint(@JsonProperty("coordinates") double [][] coordinates) {
        super();
        this.coordinates = coordinates;
        this.bbox = null;
    }

    @JsonCreator
    public MultiPoint(@JsonProperty("coordinates") double [][] coordinates,
                      @JsonProperty(value = "bbox", isRequired = OptBoolean.FALSE) double[] bbox) {
        super();
        this.coordinates = coordinates;
        this.bbox = bbox;
    }

}
