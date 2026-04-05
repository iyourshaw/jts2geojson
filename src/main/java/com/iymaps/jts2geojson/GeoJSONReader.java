package com.iymaps.jts2geojson;

import com.iymaps.geojson.GeoJSON;
import com.iymaps.geojson.GeoJSONFactory;
import com.iymaps.geojson.GeometryCollection;
import com.iymaps.geojson.LineString;
import com.iymaps.geojson.MultiLineString;
import com.iymaps.geojson.MultiPoint;
import com.iymaps.geojson.MultiPolygon;
import com.iymaps.geojson.Point;
import com.iymaps.geojson.Polygon;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.PrecisionModel;

public class GeoJSONReader {
    final static GeometryFactory FACTORY = new GeometryFactory(
            new PrecisionModel(PrecisionModel.FLOATING));

    /**
     * Converts a GeoJSON string to a JTS {@link Geometry}.
     * @param json the GeoJSON string
     * @return the JTS geometry
     */
    public Geometry read(String json) {
        return read(json, null);
    }

    /**
     * Converts a GeoJSON string to a JTS {@link Geometry}.
     * @param json the GeoJSON string
     * @param geomFactory the factory used to create geometries; uses default if null
     * @return the JTS geometry
     */
    public Geometry read(String json, GeometryFactory geomFactory) {
        GeoJSON geoJSON = GeoJSONFactory.create(json);
        return read(geoJSON, geomFactory);
    }

    /**
     * Converts a {@link GeoJSON} object to a JTS {@link Geometry}.
     * @param geoJSON the GeoJSON object
     * @return the JTS geometry
     */
    public Geometry read(GeoJSON geoJSON) {
        return read(geoJSON, null);
    }

    /**
     * Converts a {@link GeoJSON} object to a JTS {@link Geometry}.
     * @param geoJSON the GeoJSON object
     * @param geomFactory the factory used to create geometries; uses default if null
     * @return the JTS geometry
     */
    public Geometry read(GeoJSON geoJSON, GeometryFactory geomFactory) {
        GeometryFactory factory;
        if (geomFactory != null){
            factory = geomFactory;
        } else {
            factory = FACTORY;
        }

        if (geoJSON instanceof Point) {
            return convert((Point) geoJSON, factory);
        } else if (geoJSON instanceof LineString) {
            return convert((LineString) geoJSON, factory);
        } else if (geoJSON instanceof Polygon) {
            return convert((Polygon) geoJSON, factory);
        } else if (geoJSON instanceof MultiPoint) {
            return convert((MultiPoint) geoJSON, factory);
        } else if (geoJSON instanceof MultiLineString) {
            return convert((MultiLineString) geoJSON, factory);
        } else if (geoJSON instanceof MultiPolygon) {
            return convert((MultiPolygon) geoJSON, factory);
        } else if (geoJSON instanceof GeometryCollection) {
            return convert((GeometryCollection) geoJSON, factory);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    Geometry convert(Point point, GeometryFactory factory) {
        return factory.createPoint(convert(point.getCoordinates()));
    }

    Geometry convert(MultiPoint multiPoint, GeometryFactory factory) {
        return factory.createMultiPointFromCoords(convert(multiPoint.getCoordinates()));
    }

    Geometry convert(LineString lineString, GeometryFactory factory) {
        return factory.createLineString(convert(lineString.getCoordinates()));
    }

    Geometry convert(MultiLineString multiLineString, GeometryFactory factory) {
        int size = multiLineString.getCoordinates().length;
        org.locationtech.jts.geom.LineString[] lineStrings = new org.locationtech.jts.geom.LineString[size];
        for (int i = 0; i < size; i++) {
            lineStrings[i] = factory.createLineString(convert(multiLineString.getCoordinates()[i]));
        }
        return factory.createMultiLineString(lineStrings);
    }

    Geometry convert(Polygon polygon, GeometryFactory factory) {
        return convertToPolygon(polygon.getCoordinates(), factory);
    }

    org.locationtech.jts.geom.Polygon convertToPolygon(double[][][] coordinates, GeometryFactory factory) {
        LinearRing shell = factory.createLinearRing(convert(coordinates[0]));

        if (coordinates.length > 1) {
            int size = coordinates.length - 1;
            LinearRing[] holes = new LinearRing[size];
            for (int i = 0; i < size; i++) {
                holes[i] = factory.createLinearRing(convert(coordinates[i + 1]));
            }
            return factory.createPolygon(shell, holes);
        } else {
            return factory.createPolygon(shell);
        }
    }

    Geometry convert(MultiPolygon multiPolygon, GeometryFactory factory) {
        int size = multiPolygon.getCoordinates().length;
        org.locationtech.jts.geom.Polygon[] polygons = new org.locationtech.jts.geom.Polygon[size];
        for (int i = 0; i < size; i++) {
            polygons[i] = convertToPolygon(multiPolygon.getCoordinates()[i], factory);
        }
        return factory.createMultiPolygon(polygons);
    }

    Geometry convert(GeometryCollection gc, GeometryFactory factory) {
        int size = gc.getGeometries().length;
        org.locationtech.jts.geom.Geometry[] geometries = new org.locationtech.jts.geom.Geometry[size];
        for (int i = 0; i < size; i++) {
            geometries[i] = read(gc.getGeometries()[i], factory);
        }
        return factory.createGeometryCollection(geometries);
    }

    Coordinate convert(double[] c) {
        if(c.length == 2){
            return new Coordinate(c[0], c[1]);
        }
        else{
            return new Coordinate(c[0], c[1], c[2]);
        }
    }

    Coordinate[] convert(double[][] ca) {
        Coordinate[] coordinates = new Coordinate[ca.length];
        for (int i = 0; i < ca.length; i++) {
            coordinates[i] = convert(ca[i]);
        }
        return coordinates;
    }
}
