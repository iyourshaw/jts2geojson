package io.github.iyourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** GeoJSON FeatureCollection containing an array of {@link Feature} objects. */
public class FeatureCollection extends BaseFeatureCollection<Feature> {

  /**
   * Creates a FeatureCollection with the given features.
   * @param features the features in this collection
   */
  @JsonCreator
  public FeatureCollection(@JsonProperty("features") Feature[] features) {
    super(features);
  }
}
