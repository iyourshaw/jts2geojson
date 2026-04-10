package io.github.iyourshaw.jts2geojson;

import java.util.List;

import io.github.iyourshaw.geojson.FeatureCollection;
import org.locationtech.jts.geom.*;
import io.github.iyourshaw.geojson.Feature;

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
    public io.github.iyourshaw.geojson.Geometry write(Geometry geometry) {
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

    io.github.iyourshaw.geojson.Point convert(Point point) {
        io.github.iyourshaw.geojson.Point json = new io.github.iyourshaw.geojson.Point(
                convert(point.getCoordinate()));
        return json;
    }

    io.github.iyourshaw.geojson.MultiPoint convert(MultiPoint multiPoint) {
        return new io.github.iyourshaw.geojson.MultiPoint(
                convert(multiPoint.getCoordinates()));
    }

    io.github.iyourshaw.geojson.LineString convert(LineString lineString) {
        return new io.github.iyourshaw.geojson.LineString(
                convert(lineString.getCoordinates()));
    }

    io.github.iyourshaw.geojson.LineString convert(LinearRing ringString) {
        return new io.github.iyourshaw.geojson.LineString(
                convert(ringString.getCoordinates()));
    }

    io.github.iyourshaw.geojson.MultiLineString convert(MultiLineString multiLineString) {
        int size = multiLineString.getNumGeometries();
        double[][][] lineStrings = new double[size][][];
        for (int i = 0; i < size; i++) {
            lineStrings[i] = convert(multiLineString.getGeometryN(i).getCoordinates());
        }
        return new io.github.iyourshaw.geojson.MultiLineString(lineStrings);
    }

    io.github.iyourshaw.geojson.Polygon convert(Polygon polygon) {
        int size = polygon.getNumInteriorRing() + 1;
        double[][][] rings = new double[size][][];
        rings[0] = convert(polygon.getExteriorRing().getCoordinates());
        for (int i = 0; i < size - 1; i++) {
            rings[i + 1] = convert(polygon.getInteriorRingN(i).getCoordinates());
        }
        return new io.github.iyourshaw.geojson.Polygon(rings);
    }

    io.github.iyourshaw.geojson.MultiPolygon convert(MultiPolygon multiPolygon) {
        int size = multiPolygon.getNumGeometries();
        double[][][][] polygons = new double[size][][][];
        for (int i = 0; i < size; i++) {
            polygons[i] = convert((Polygon) multiPolygon.getGeometryN(i)).getCoordinates();
        }
        return new io.github.iyourshaw.geojson.MultiPolygon(polygons);
    }

    io.github.iyourshaw.geojson.GeometryCollection convert(GeometryCollection gc) {
        int size = gc.getNumGeometries();
        io.github.iyourshaw.geojson.Geometry[] geometries = new io.github.iyourshaw.geojson.Geometry[size];
        for (int i = 0; i < size; i++) {
            geometries[i] = write((Geometry) gc.getGeometryN(i));
        }
        return new io.github.iyourshaw.geojson.GeometryCollection(geometries);
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
