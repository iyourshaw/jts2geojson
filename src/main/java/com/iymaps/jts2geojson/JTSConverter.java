package com.iymaps.jts2geojson;


import com.iymaps.geojson.LineString;
import com.iymaps.geojson.MultiLineString;
import com.iymaps.geojson.MultiPoint;
import com.iymaps.geojson.MultiPolygon;
import com.iymaps.geojson.Point;
import com.iymaps.geojson.Polygon;

/**
 * Static utility methods to convert between GeoJSON model objects
 * and JTS geometries.
 */
public class JTSConverter {

    private JTSConverter() {
        // hidden constructor, static use only
    }

    /**
     * Converts a GeoJSON MultiLineString to a JTS MultiLineString.
     * @param multiLineString the GeoJSON MultiLineString
     * @return the JTS MultiLineString
     */
    public static org.locationtech.jts.geom.MultiLineString convertToJTS(MultiLineString multiLineString) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.MultiLineString)reader.convert(multiLineString, GeoJSONReader.FACTORY);
    }

    /**
     * Converts a GeoJSON LineString to a JTS LineString.
     * @param lineString the GeoJSON LineString
     * @return the JTS LineString
     */
    public static org.locationtech.jts.geom.LineString convertToJTS(LineString lineString) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.LineString)reader.convert(lineString, GeoJSONReader.FACTORY);
    }

    /**
     * Converts a GeoJSON Point to a JTS Point.
     * @param point the GeoJSON Point
     * @return the JTS Point
     */
    public static org.locationtech.jts.geom.Point convertToJTS(Point point) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.Point)reader.convert(point, GeoJSONReader.FACTORY);
    }

    /**
     * Converts a GeoJSON Polygon to a JTS Polygon.
     * @param polygon the GeoJSON Polygon
     * @return the JTS Polygon
     */
    public static org.locationtech.jts.geom.Polygon convertToJTS(Polygon polygon) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.Polygon)reader.convert(polygon, GeoJSONReader.FACTORY);
    }

    /**
     * Converts a GeoJSON MultiPolygon to a JTS MultiPolygon.
     * @param multiPolygon the GeoJSON MultiPolygon
     * @return the JTS MultiPolygon
     */
    public static org.locationtech.jts.geom.MultiPolygon convertToJTS(MultiPolygon multiPolygon) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.MultiPolygon)reader.convert(multiPolygon, GeoJSONReader.FACTORY);
    }

    /**
     * Converts a GeoJSON MultiPoint to a JTS MultiPoint.
     * @param multiPoint the GeoJSON MultiPoint
     * @return the JTS MultiPoint
     */
    public static org.locationtech.jts.geom.MultiPoint convertToJTS(MultiPoint multiPoint) {
        GeoJSONReader reader = new GeoJSONReader();
        return (org.locationtech.jts.geom.MultiPoint)reader.convert(multiPoint, GeoJSONReader.FACTORY);
    }

    /**
     * Converts a JTS MultiLineString to a GeoJSON MultiLineString.
     * @param multiLineString the JTS MultiLineString
     * @return the GeoJSON MultiLineString
     */
    public static MultiLineString convertFromJTS(org.locationtech.jts.geom.MultiLineString multiLineString) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return writer.convert(multiLineString);
    }

    /**
     * Converts a JTS LineString to a GeoJSON LineString.
     * @param lineString the JTS LineString
     * @return the GeoJSON LineString
     */
    public static LineString convertFromJTS(org.locationtech.jts.geom.LineString lineString) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return writer.convert(lineString);
    }

    /**
     * Converts a JTS Point to a GeoJSON Point.
     * @param point the JTS Point
     * @return the GeoJSON Point
     */
    public static Point convertFromJTS(org.locationtech.jts.geom.Point point) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return writer.convert(point);
    }

    /**
     * Converts a JTS MultiPoint to a GeoJSON MultiPoint.
     * @param multiPoint the JTS MultiPoint
     * @return the GeoJSON MultiPoint
     */
    public static MultiPoint convertFromJTS(org.locationtech.jts.geom.MultiPoint multiPoint) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return writer.convert(multiPoint);
    }

    /**
     * Converts a JTS Polygon to a GeoJSON Polygon.
     * @param polygon the JTS Polygon
     * @return the GeoJSON Polygon
     */
    public static Polygon convertFromJTS(org.locationtech.jts.geom.Polygon polygon) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return writer.convert(polygon);
    }

    /**
     * Converts a JTS MultiPolygon to a GeoJSON MultiPolygon.
     * @param multiPolygon the JTS MultiPolygon
     * @return the GeoJSON MultiPolygon
     */
    public static MultiPolygon convertFromJTS(org.locationtech.jts.geom.MultiPolygon multiPolygon) {
        GeoJSONWriter writer = new GeoJSONWriter();
        return writer.convert(multiPolygon);
    }
}
