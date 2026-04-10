package io.github.iyourshaw.geojson;

import lombok.Getter;

/**
 * A GeoJSON feature with a JTS geometry.
 * Not serializable to JSON without converting to a geojson Feature.
 *
 * @param <TGeometry> the JTS geometry type
 * @param <TProperties> the properties type
 */
@Getter
public abstract class BaseJTSFeature<TGeometry extends org.locationtech.jts.geom.Geometry, TProperties> {

    /** The JTS geometry of this feature. */
    protected TGeometry geometry;
    /** The properties of this feature. */
    protected TProperties properties;

    /**
     * Creates a feature with the given geometry and properties.
     * @param geometry the JTS geometry
     * @param properties the feature properties
     */
    public BaseJTSFeature(TGeometry geometry, TProperties properties) {
        this.geometry = geometry;
        this.properties = properties;
    }

}
