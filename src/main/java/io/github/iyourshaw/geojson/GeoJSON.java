package io.github.iyourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.ObjectMapper;

/**
 * Root type for all GeoJSON objects. Subclasses serialize to a GeoJSON JSON string via {@link #toString()}.
 */
public abstract class GeoJSON {
  private static final ObjectMapper mapper = new ObjectMapper();

  /** For use by Jackson during deserialization. */
  @JsonCreator
  public GeoJSON() {}

  /**
   * Serializes this object to a GeoJSON JSON string.
   * @return the GeoJSON JSON string
   */
  public String toString() {
    return mapper.writeValueAsString(this);
  }

  /**
   * Override with the name to be used as the type attribute in
   * the generated GeoJSON for the element.
   *
   * @return the GeoJSON type string (e.g. {@code "Point"}, {@code "Feature"})
   */
  @JsonIgnore
  protected abstract String getGeoJSONType();

  /**
   * Returns the GeoJSON type name used in the {@code "type"} field.
   *
   * @return the GeoJSON type string
   */
  @JsonProperty("type")
  public String getType() {
    return getGeoJSONType();
  }

  /**
   * No-op; the {@code "type"} field is not deserializable.
   *
   * @param type ignored
   */
  @JsonIgnore
  public void setType(String type) {
    // Does nothing, to make type not deserializable
  }
}
