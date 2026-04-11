package io.github.iyourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

/**
 * Base class for a typed GeoJSON FeatureCollection.
 * Extend this class to define a FeatureCollection with a specific
 * {@link BaseFeature} subtype.
 *
 * @param <TFeature> the feature type contained in this collection
 */
@Getter
@JsonPropertyOrder({"type", "features"})
public abstract class BaseFeatureCollection<TFeature extends BaseFeature<?, ?, ?>> extends GeoJSON {

  @Override
  public String getGeoJSONType() {
    return "FeatureCollection";
  }

  private final TFeature[] features;

  /**
   * Creates a collection with the given features.
   * @param features the features in this collection
   */
  @JsonCreator
  public BaseFeatureCollection(@JsonProperty("features") TFeature[] features) {
    super();
    this.features = features;
  }
}
