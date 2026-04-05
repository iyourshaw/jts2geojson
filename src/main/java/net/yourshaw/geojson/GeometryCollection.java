package net.yourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"type", "geometries"})
public class GeometryCollection extends Geometry {
    private final Geometry[] geometries;

    @JsonCreator
    public GeometryCollection(@JsonProperty("geometries") Geometry[] geometries) {
        super();
        this.geometries = geometries;
    }

}
