package io.github.iyourshaw.geojson.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.iyourshaw.geojson.BaseFeature;
import io.github.iyourshaw.geojson.Point;
import java.util.UUID;

/**
 * Example feature class with an immutable properties POJO, Point geometry, and UUID as ID.
 */
public class ExampleImmutablePojoFeature
    extends BaseFeature<UUID, Point, ExampleImmutablePojoProperties> {

  @JsonCreator
  public ExampleImmutablePojoFeature(
      @JsonProperty("id") UUID id,
      @JsonProperty("geometry") Point geometry,
      @JsonProperty("properties") ExampleImmutablePojoProperties properties) {
    super(id, geometry, properties);
  }
}
