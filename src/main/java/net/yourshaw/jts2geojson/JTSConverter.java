package net.yourshaw.jts2geojson;


import net.yourshaw.geojson.*;

/**
 * Static utility methods to convert geojson derived Geometries to JTS Geometries
 */
public class JTSConverter {
    
    public static org.locationtech.jts.geom.MultiLineString convertToJTS(MultiLineString multiLineString) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.MultiLineString)reader.convert(multiLineString, GeoJSONReader.FACTORY);
    }

    public static org.locationtech.jts.geom.LineString convertToJTS(LineString lineString) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.LineString)reader.convert(lineString, GeoJSONReader.FACTORY);
    }

    public static org.locationtech.jts.geom.Point convertToJTS(Point point) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.Point)reader.convert(point, GeoJSONReader.FACTORY);
    }

    public static org.locationtech.jts.geom.Polygon convertoToJTS(Polygon polygon) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.Polygon)reader.convert(polygon, GeoJSONReader.FACTORY);
    }

    public static org.locationtech.jts.geom.MultiPolygon convertoToJTS(MultiPolygon multiPolygon) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.MultiPolygon)reader.convert(multiPolygon, GeoJSONReader.FACTORY);
    }

    public static org.locationtech.jts.geom.MultiPoint convertoToJTS(MultiPoint polygon) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.MultiPoint)reader.convert(polygon, GeoJSONReader.FACTORY);
    }

    public static MultiLineString convertFromJTS(org.locationtech.jts.geom.MultiLineString multiLineString) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return (MultiLineString)writer.convert(multiLineString);
    }

    public static LineString convertFromJTS(org.locationtech.jts.geom.LineString lineString) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return (LineString)writer.convert(lineString);
    }

    public static Point convertFromJTS(org.locationtech.jts.geom.Point point) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return (Point)writer.convert(point);
    }

    public static MultiPoint convertFromJTS(org.locationtech.jts.geom.MultiPoint multiPoint) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return (MultiPoint)writer.convert(multiPoint);
    }

    public static Polygon convertFromJTS(org.locationtech.jts.geom.Polygon polygon) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return (Polygon)writer.convert(polygon);
    }

    public static MultiPolygon convertFromJTS(org.locationtech.jts.geom.MultiPolygon multiPolygon) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return (MultiPolygon)writer.convert(multiPolygon);
    }
}
