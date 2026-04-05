package net.yourshaw.geojson;

import lombok.Getter;

/**
 * A GeoJSON feature with a JTS geometry
 * Not serializable to JSON without converting to a geojson Feature
 */
@Getter
public abstract class BaseJTSFeature<TGeometry extends org.locationtech.jts.geom.Geometry, TProperties> {
    
    protected TGeometry geometry;
    protected TProperties properties;

    public BaseJTSFeature(TGeometry geometry, TProperties properties) {
        this.geometry = geometry;
        this.properties = properties;
    }

}
