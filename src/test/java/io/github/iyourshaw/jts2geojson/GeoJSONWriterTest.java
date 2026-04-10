package io.github.iyourshaw.jts2geojson;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import io.github.iyourshaw.geojson.Feature;
import io.github.iyourshaw.geojson.FeatureCollection;
import io.github.iyourshaw.geojson.Properties;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeoJSONWriterTest {

    public static GeoJSONReader reader;
    public static GeoJSONWriter writer;
    public static GeometryFactory factory;
    public static Point point;
    public static LineString lineString;
    public static Polygon polygon;

    @BeforeAll
    public static void setup() {
        reader = new GeoJSONReader();
        writer = new GeoJSONWriter();
        factory = new GeometryFactory();
        point = factory.createPoint(new Coordinate(1, 1));
        lineString = factory.createLineString(new Coordinate[] {
                new Coordinate(1, 1),
                new Coordinate(1, 2),
                new Coordinate(2, 2),
                new Coordinate(1, 1)
        });
        polygon = factory.createPolygon(lineString.getCoordinates());

    }

    @Test
    public void writePointTest() {
        var expected = """
                {"type":"Point","coordinates":[1.0,1.0]}""";

        var json = writer.write(point);
        assertThat(expected, jsonEquals(json.toString()));

        var geometry = reader.read(json);
        assertThat("POINT (1 1)", equalTo(geometry.toString()));
    }

    @Test
    public void writeLineStringTest() {
        var json = writer.write(lineString);
        assertThat("""
                {"type":"LineString","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""",
                jsonEquals(json.toString()));
    }

    @Test
    public void writeLinearRingTest() {
        var ring = factory.createLinearRing(lineString.getCoordinates());
        var json = writer.write(ring);
        assertThat("""
                {"type":"LineString","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""",
                jsonEquals(json.toString()));
    }

    @Test
    public void writePolygonTest() {
        var json = writer.write(polygon);
        assertThat("""
                {"type":"Polygon","coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]}""",
                jsonEquals(json.toString()));
    }

    @Test
    public void writePolygonWithHoleTest() {
        var shell = factory.createLinearRing(new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(10, 0),
                new Coordinate(10, 10), new Coordinate(0, 10),
                new Coordinate(0, 0)
        });
        var hole = factory.createLinearRing(new Coordinate[]{
                new Coordinate(2, 2), new Coordinate(4, 2),
                new Coordinate(4, 4), new Coordinate(2, 4),
                new Coordinate(2, 2)
        });
        var polygonWithHole = factory.createPolygon(shell, new org.locationtech.jts.geom.LinearRing[]{hole});
        var json = writer.write(polygonWithHole);
        assertThat("""
                {"type":"Polygon","coordinates":[
                    [[0.0,0.0],[10.0,0.0],[10.0,10.0],[0.0,10.0],[0.0,0.0]],
                    [[2.0,2.0],[4.0,2.0],[4.0,4.0],[2.0,4.0],[2.0,2.0]]
                ]}""",
                jsonEquals(json.toString()));
    }

    @Test
    public void writeMultiPointTest() {
        var multiPoint = factory.createMultiPointFromCoords(lineString.getCoordinates());
        var json = writer.write(multiPoint);
        assertThat("""
                {"type":"MultiPoint","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""",
                jsonEquals(json.toString()));
    }

    @Test
    public void writeMultiLineStringTest() {
        var multiLineString = factory.createMultiLineString(new LineString[]{lineString, lineString});
        var json = writer.write(multiLineString);
        assertThat("""
                {"type":"MultiLineString","coordinates":[
                    [[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]],
                    [[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]
                ]}""",
                jsonEquals(json.toString()));
    }

    @Test
    public void writeMultiPolygonTest() {
        var multiPolygon = factory.createMultiPolygon(new Polygon[]{polygon, polygon});
        var json = writer.write(multiPolygon);
        assertThat("""
                {"type":"MultiPolygon","coordinates":[
                    [[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]],
                    [[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]
                ]}""",
                jsonEquals(json.toString()));
    }

    @Test
    public void writeGeometryCollectionTest() {
        var geometryCollection = factory.createGeometryCollection(new Geometry[]{point, lineString, polygon});
        var json = writer.write(geometryCollection);
        assertThat("""
                {"type":"GeometryCollection","geometries":[
                    {"type":"Point","coordinates":[1.0,1.0]},
                    {"type":"LineString","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]},
                    {"type":"Polygon","coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]}
                ]}""",
                jsonEquals(json.toString()));
    }

    @Test
    public void writeFeatureListTest() {
        var pointJson = writer.write(point);
        var polygonJson = writer.write(polygon);
        var props1 = new Properties();
        props1.put("name", "point feature");
        props1.put("index", 1);
        var props2 = new Properties();
        props2.put("name", "polygon feature");
        props2.put("index", 2);
        var features = List.of(new Feature(pointJson, props1), new Feature(polygonJson, props2));
        var featureCollection = writer.write(features);
        assertThat("""
                {"type":"FeatureCollection","features":[
                    {"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"name":"point feature","index":1}},
                    {"type":"Feature","geometry":{"type":"Polygon","coordinates":[[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]]},"properties":{"name":"polygon feature","index":2}}
                ]}""",
                jsonEquals(featureCollection.toString()));
    }

    @Test
    public void writeUnsupportedTypeThrowsTest() {
        // Anonymous subclass won't match any of the exact class checks in write()
        var unsupported = new org.locationtech.jts.geom.GeometryCollection(new Geometry[0], factory) {};
        assertThrows(UnsupportedOperationException.class, () -> writer.write(unsupported));
    }

    @Test
    public void writeFeatureCollectionTest() {
        var json = writer.write(point);
        var feature1 = new Feature(json, null);
        var feature2 = new Feature(json, null);
        var featureCollection = new FeatureCollection(new Feature[]{feature1, feature2});
        assertThat("""
                {"type":"FeatureCollection","features":[
                    {"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":null},
                    {"type":"Feature","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":null}
                ]}""",
                jsonEquals(featureCollection.toString()));
    }


}
