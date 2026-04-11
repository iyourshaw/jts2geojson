package io.github.iyourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Getter;

/** GeoJSON MultiPolygon geometry. Coordinates are an array of polygons. */
@Getter
@JsonInclude(Include.NON_NULL)
public class MultiPolygon extends Geometry {
  private final double[][][][] coordinates;
  private final double[] bbox;

  /**
   * Creates a MultiPolygon with the given coordinates.
   * @param coordinates array of polygons
   */
  public MultiPolygon(@JsonProperty("coordinates") double[][][][] coordinates) {
    super();
    this.coordinates = coordinates;
    this.bbox = null;
  }

  /**
   * Creates a MultiPolygon with the given coordinates and bounding box.
   * @param coordinates array of polygons
   * @param bbox optional bounding box, or {@code null}
   */
  @JsonCreator
  public MultiPolygon(
      @JsonProperty("coordinates") double[][][][] coordinates,
      @JsonProperty(value = "bbox", isRequired = OptBoolean.FALSE) double[] bbox) {
    super();
    this.coordinates = coordinates;
    this.bbox = bbox;
  }
}
