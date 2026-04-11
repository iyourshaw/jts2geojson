package io.github.iyourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

/** GeoJSON GeometryCollection. Contains an array of geometry objects. */
@Getter
@Setter
@JsonPropertyOrder({"type", "geometries"})
public class GeometryCollection extends Geometry {
  private final Geometry[] geometries;

  /**
   * Creates a GeometryCollection with the given geometries.
   * @param geometries the geometries in this collection
   */
  @JsonCreator
  public GeometryCollection(@JsonProperty("geometries") Geometry[] geometries) {
    super();
    this.geometries = geometries;
  }
}
