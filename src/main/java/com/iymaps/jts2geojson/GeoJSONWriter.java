package com.iymaps.jts2geojson;

import java.util.List;

import com.iymaps.geojson.FeatureCollection;
import org.locationtech.jts.geom.*;
import com.iymaps.geojson.Feature;

/** Converts JTS {@link org.locationtech.jts.geom.Geometry} objects to GeoJSON model objects. */
public class GeoJSONWriter {

    /** Creates a new GeoJSONWriter. */
    public GeoJSONWriter() {
    }

    final static GeoJSONReader reader = new GeoJSONReader();
        
    /**
     * Converts a JTS geometry to a GeoJSON model object.
     * @param geometry the JTS geometry
     * @return the corresponding GeoJSON geometry object
     * @throws UnsupportedOperationException if the geometry type is not supported
     */
    public com.iymaps.geojson.Geometry write(Geometry geometry) {
        Class<? extends Geometry> c = geometry.getClass();
        if (c.equals(Point.class)) {
            return convert((Point) geometry);
        } else if (c.equals(LineString.class)) {
            return convert((LineString) geometry);
        } else if (c.equals(LinearRing.class)) {
            return convert((LinearRing) geometry);
        } else if (c.equals(Polygon.class)) {
            return convert((Polygon) geometry);
        } else if (c.equals(MultiPoint.class)) {
            return convert((MultiPoint) geometry);
        } else if (c.equals(MultiLineString.class)) {
            return convert((MultiLineString) geometry);
        } else if (c.equals(MultiPolygon.class)) {
            return convert((MultiPolygon) geometry);
        } else if (c.equals(GeometryCollection.class)) {
            return convert((GeometryCollection) geometry);
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * Wraps a list of features in a {@link FeatureCollection}.
     * @param features the features
     * @return a GeoJSON FeatureCollection
     */
    public FeatureCollection write(List<Feature> features) {
        int size = features.size();
        Feature[] featuresJson = new Feature[size];
        for (int i=0; i<size; i++) {
            featuresJson[i] = features.get(i);
        }
        return new FeatureCollection(featuresJson);
    }

    com.iymaps.geojson.Point convert(Point point) {
        com.iymaps.geojson.Point json = new com.iymaps.geojson.Point(
                convert(point.getCoordinate()));
        return json;
    }

    com.iymaps.geojson.MultiPoint convert(MultiPoint multiPoint) {
        return new com.iymaps.geojson.MultiPoint(
                convert(multiPoint.getCoordinates()));
    }

    com.iymaps.geojson.LineString convert(LineString lineString) {
        return new com.iymaps.geojson.LineString(
                convert(lineString.getCoordinates()));
    }

    com.iymaps.geojson.LineString convert(LinearRing ringString) {
        return new com.iymaps.geojson.LineString(
                convert(ringString.getCoordinates()));
    }

    com.iymaps.geojson.MultiLineString convert(MultiLineString multiLineString) {
        int size = multiLineString.getNumGeometries();
        double[][][] lineStrings = new double[size][][];
        for (int i = 0; i < size; i++) {
            lineStrings[i] = convert(multiLineString.getGeometryN(i).getCoordinates());
        }
        return new com.iymaps.geojson.MultiLineString(lineStrings);
    }

    com.iymaps.geojson.Polygon convert(Polygon polygon) {
        int size = polygon.getNumInteriorRing() + 1;
        double[][][] rings = new double[size][][];
        rings[0] = convert(polygon.getExteriorRing().getCoordinates());
        for (int i = 0; i < size - 1; i++) {
            rings[i + 1] = convert(polygon.getInteriorRingN(i).getCoordinates());
        }
        return new com.iymaps.geojson.Polygon(rings);
    }

    com.iymaps.geojson.MultiPolygon convert(MultiPolygon multiPolygon) {
        int size = multiPolygon.getNumGeometries();
        double[][][][] polygons = new double[size][][][];
        for (int i = 0; i < size; i++) {
            polygons[i] = convert((Polygon) multiPolygon.getGeometryN(i)).getCoordinates();
        }
        return new com.iymaps.geojson.MultiPolygon(polygons);
    }

    com.iymaps.geojson.GeometryCollection convert(GeometryCollection gc) {
        int size = gc.getNumGeometries();
        com.iymaps.geojson.Geometry[] geometries = new com.iymaps.geojson.Geometry[size];
        for (int i = 0; i < size; i++) {
            geometries[i] = write((Geometry) gc.getGeometryN(i));
        }
        return new com.iymaps.geojson.GeometryCollection(geometries);
    }

    double[] convert(Coordinate coordinate) {
        if(Double.isNaN( coordinate.getZ() )) {
            return new double[] { coordinate.x, coordinate.y };
        }
        else {
            return new double[] { coordinate.x, coordinate.y, coordinate.getZ() };
        }
    }

    double[][] convert(Coordinate[] coordinates) {
        double[][] array = new double[coordinates.length][];
        for (int i = 0; i < coordinates.length; i++) {
            array[i] = convert(coordinates[i]);
        }
        return array;
    }
}
