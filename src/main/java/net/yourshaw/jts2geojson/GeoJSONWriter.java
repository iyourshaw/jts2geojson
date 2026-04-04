package net.yourshaw.jts2geojson;

import java.util.List;

import net.yourshaw.geojson.FeatureCollection;
import org.locationtech.jts.geom.*;
import net.yourshaw.geojson.Feature;

public class GeoJSONWriter {

    final static GeoJSONReader reader = new GeoJSONReader();
        
    public net.yourshaw.geojson.Geometry write(Geometry geometry) {
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
    
    public FeatureCollection write(List<Feature> features) {
        int size = features.size();
        Feature[] featuresJson = new Feature[size];
        for (int i=0; i<size; i++) {
            featuresJson[i] = features.get(i);
        }
        return new FeatureCollection(featuresJson);
    }

    net.yourshaw.geojson.Point convert(Point point) {
        net.yourshaw.geojson.Point json = new net.yourshaw.geojson.Point(
                convert(point.getCoordinate()));
        return json;
    }

    net.yourshaw.geojson.MultiPoint convert(MultiPoint multiPoint) {
        return new net.yourshaw.geojson.MultiPoint(
                convert(multiPoint.getCoordinates()));
    }

    net.yourshaw.geojson.LineString convert(LineString lineString) {
        return new net.yourshaw.geojson.LineString(
                convert(lineString.getCoordinates()));
    }

    net.yourshaw.geojson.LineString convert(LinearRing ringString) {
        return new net.yourshaw.geojson.LineString(
                convert(ringString.getCoordinates()));
    }

    net.yourshaw.geojson.MultiLineString convert(MultiLineString multiLineString) {
        int size = multiLineString.getNumGeometries();
        double[][][] lineStrings = new double[size][][];
        for (int i = 0; i < size; i++) {
            lineStrings[i] = convert(multiLineString.getGeometryN(i).getCoordinates());
        }
        return new net.yourshaw.geojson.MultiLineString(lineStrings);
    }

    net.yourshaw.geojson.Polygon convert(Polygon polygon) {
        int size = polygon.getNumInteriorRing() + 1;
        double[][][] rings = new double[size][][];
        rings[0] = convert(polygon.getExteriorRing().getCoordinates());
        for (int i = 0; i < size - 1; i++) {
            rings[i + 1] = convert(polygon.getInteriorRingN(i).getCoordinates());
        }
        return new net.yourshaw.geojson.Polygon(rings);
    }

    net.yourshaw.geojson.MultiPolygon convert(MultiPolygon multiPolygon) {
        int size = multiPolygon.getNumGeometries();
        double[][][][] polygons = new double[size][][][];
        for (int i = 0; i < size; i++) {
            polygons[i] = convert((Polygon) multiPolygon.getGeometryN(i)).getCoordinates();
        }
        return new net.yourshaw.geojson.MultiPolygon(polygons);
    }

    net.yourshaw.geojson.GeometryCollection convert(GeometryCollection gc) {
        int size = gc.getNumGeometries();
        net.yourshaw.geojson.Geometry[] geometries = new net.yourshaw.geojson.Geometry[size];
        for (int i = 0; i < size; i++) {
            geometries[i] = write((Geometry) gc.getGeometryN(i));
        }
        return new net.yourshaw.geojson.GeometryCollection(geometries);
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
