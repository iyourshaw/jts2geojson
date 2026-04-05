package com.iymaps.geojson.examples;

import com.iymaps.geojson.BaseJTSFeature;
import org.locationtech.jts.geom.Point;

/**
 * Example concrete subclass of {@link BaseJTSFeature} using a JTS Point
 * geometry and {@link ExamplePojoProperties}.
 */
public class ExampleJTSFeature extends BaseJTSFeature<Point, ExamplePojoProperties> {

    public ExampleJTSFeature(Point geometry, ExamplePojoProperties properties) {
        super(geometry, properties);
    }

}
