package io.github.iyourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Getter;

/**
 * GeoJSON Point geometry. Coordinates are {@code [longitude, latitude]}
 * or {@code [longitude, latitude, elevation]}.
 */
@JsonInclude(Include.NON_NULL)
@Getter
public class Point extends Geometry {
  private final double[] coordinates;
  private final double[] bbox;

  /**
   * Creates a 2D point.
   * @param longitude longitude (x)
   * @param latitude latitude (y)
   */
  public Point(double longitude, double latitude) {
    super();
    this.coordinates = new double[] {longitude, latitude};
    this.bbox = null;
  }

  /**
   * Creates a Point with the given coordinates.
   * @param coordinates position as {@code [longitude, latitude]}
   *                    or {@code [longitude, latitude, elevation]}
   */
  public Point(@JsonProperty("coordinates") double[] coordinates) {
    super();
    this.coordinates = coordinates;
    this.bbox = null;
  }

  /**
   * Creates a Point with the given coordinates and bounding box.
   * @param coordinates position as {@code [longitude, latitude]}
   *                    or {@code [longitude, latitude, elevation]}
   * @param bbox optional bounding box, or {@code null}
   */
  @JsonCreator
  public Point(
      @JsonProperty("coordinates") double[] coordinates,
      @JsonProperty(value = "bbox", isRequired = OptBoolean.FALSE) double[] bbox) {
    super();
    this.coordinates = coordinates;
    this.bbox = bbox;
  }
}
