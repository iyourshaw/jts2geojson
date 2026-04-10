package com.iymaps.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;


/**
 * Base class for a feature class.
 * 
 * @param <TId> The type of the feature ID.
 * @param <TGeometry> The geometry type of the feature
 * @param <TProperties> A class containing GeoJSON properties that can be either a Map or a POJO class
 */
@Getter
@JsonPropertyOrder({"type", "id", "geometry", "properties"})
public abstract class BaseFeature<TId, TGeometry extends Geometry, TProperties> 
    extends GeoJSON {

    @Override
    public String getGeoJSONType() {
        return "Feature";
    }

    /** The feature id, or {@code null} if absent. */
    @JsonInclude(Include.NON_EMPTY)
    protected final TId id;
    /** The feature geometry. */
    protected final TGeometry geometry;
    /** The feature properties. */
    protected final TProperties properties;

    /**
     * Creates a feature with no id.
     * @param geometry the feature geometry
     * @param properties the feature properties
     */
    @JsonCreator
    public BaseFeature(
            @JsonProperty("geometry") TGeometry geometry,
            @JsonProperty("properties") TProperties properties) {
        super();
        this.id = null;
        this.geometry = geometry;
        this.properties = properties;
    }

    

    /**
     * Creates a feature with an id.
     * @param id the feature id
     * @param geometry the feature geometry
     * @param properties the feature properties
     */
    @JsonCreator
    public BaseFeature(
            @JsonProperty("id") TId id,
            @JsonProperty("geometry") TGeometry geometry,
            @JsonProperty("properties") TProperties properties) {
        super();
        this.id = id;
        this.geometry = geometry;
        this.properties = properties;
    }

}
