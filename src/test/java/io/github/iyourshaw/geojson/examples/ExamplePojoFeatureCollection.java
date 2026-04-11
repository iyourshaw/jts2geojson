package io.github.iyourshaw.geojson.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.iyourshaw.geojson.BaseFeatureCollection;

public class ExamplePojoFeatureCollection extends BaseFeatureCollection<ExamplePojoFeature> {

  @JsonCreator
  public ExamplePojoFeatureCollection(@JsonProperty("features") ExamplePojoFeature[] features) {
    super(features);
  }
}
