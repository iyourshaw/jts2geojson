package com.iymaps.jts2geojson;

import com.iymaps.geojson.GeoJSONFactory;
import com.iymaps.geojson.LineString;
import com.iymaps.geojson.MultiLineString;
import com.iymaps.geojson.MultiPoint;
import com.iymaps.geojson.MultiPolygon;
import com.iymaps.geojson.Point;
import com.iymaps.geojson.Polygon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JTSConverterTest {

    static GeometryFactory factory;
    static org.locationtech.jts.geom.Point jtsPoint;
    static org.locationtech.jts.geom.LineString jtsLineString;
    static org.locationtech.jts.geom.Polygon jtsPolygon;
    static org.locationtech.jts.geom.MultiPoint jtsMultiPoint;
    static org.locationtech.jts.geom.MultiLineString jtsMultiLineString;
    static org.locationtech.jts.geom.MultiPolygon jtsMultiPolygon;

    @BeforeAll
    static void setup() {
        factory = new GeometryFactory();
        jtsPoint = factory.createPoint(new Coordinate(1, 2));
        var lineCoords = new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(1, 1), new Coordinate(2, 0)
        };
        jtsLineString = factory.createLineString(lineCoords);
        var ringCoords = new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(4, 0),
                new Coordinate(4, 4), new Coordinate(0, 4),
                new Coordinate(0, 0)
        };
        jtsPolygon = factory.createPolygon(ringCoords);
        jtsMultiPoint = factory.createMultiPointFromCoords(lineCoords);
        jtsMultiLineString = factory.createMultiLineString(
                new org.locationtech.jts.geom.LineString[]{jtsLineString, jtsLineString});
        jtsMultiPolygon = factory.createMultiPolygon(
                new org.locationtech.jts.geom.Polygon[]{jtsPolygon, jtsPolygon});
    }

    // convertFromJTS tests

    @Test
    void convertPointFromJTSTest() {
        Point result = JTSConverter.convertFromJTS(jtsPoint);
        assertThat("""
                {"type":"Point","coordinates":[1.0,2.0]}""",
                jsonEquals(result.toString()));
    }

    @Test
    void convertLineStringFromJTSTest() {
        LineString result = JTSConverter.convertFromJTS(jtsLineString);
        assertThat("""
                {"type":"LineString","coordinates":[[0.0,0.0],[1.0,1.0],[2.0,0.0]]}""",
                jsonEquals(result.toString()));
    }

    @Test
    void convertPolygonFromJTSTest() {
        Polygon result = JTSConverter.convertFromJTS(jtsPolygon);
        assertThat("""
                {"type":"Polygon","coordinates":[
                    [[0.0,0.0],[4.0,0.0],[4.0,4.0],[0.0,4.0],[0.0,0.0]]
                ]}""",
                jsonEquals(result.toString()));
    }

    @Test
    void convertMultiPointFromJTSTest() {
        MultiPoint result = JTSConverter.convertFromJTS(jtsMultiPoint);
        assertThat("""
                {"type":"MultiPoint","coordinates":[[0.0,0.0],[1.0,1.0],[2.0,0.0]]}""",
                jsonEquals(result.toString()));
    }

    @Test
    void convertMultiLineStringFromJTSTest() {
        MultiLineString result = JTSConverter.convertFromJTS(jtsMultiLineString);
        assertThat("""
                {"type":"MultiLineString","coordinates":[
                    [[0.0,0.0],[1.0,1.0],[2.0,0.0]],
                    [[0.0,0.0],[1.0,1.0],[2.0,0.0]]
                ]}""",
                jsonEquals(result.toString()));
    }

    @Test
    void convertMultiPolygonFromJTSTest() {
        MultiPolygon result = JTSConverter.convertFromJTS(jtsMultiPolygon);
        assertThat("""
                {"type":"MultiPolygon","coordinates":[
                    [[[0.0,0.0],[4.0,0.0],[4.0,4.0],[0.0,4.0],[0.0,0.0]]],
                    [[[0.0,0.0],[4.0,0.0],[4.0,4.0],[0.0,4.0],[0.0,0.0]]]
                ]}""",
                jsonEquals(result.toString()));
    }

    // convertToJTS tests

    @Test
    void convertPointToJTSTest() {
        Point geoPoint = (Point) GeoJSONFactory.create("""
                {"type":"Point","coordinates":[1.0,2.0]}""");
        assertThat(jtsPoint, equalTo(JTSConverter.convertToJTS(geoPoint)));
    }

    @Test
    void convertLineStringToJTSTest() {
        LineString geoLineString = (LineString) GeoJSONFactory.create("""
                {"type":"LineString","coordinates":[[0.0,0.0],[1.0,1.0],[2.0,0.0]]}""");
        assertThat(jtsLineString, equalTo(JTSConverter.convertToJTS(geoLineString)));
    }

    @Test
    void convertPolygonToJTSTest() {
        Polygon geoPolygon = (Polygon) GeoJSONFactory.create("""
                {"type":"Polygon","coordinates":[
                    [[0.0,0.0],[4.0,0.0],[4.0,4.0],[0.0,4.0],[0.0,0.0]]
                ]}""");
        assertThat(jtsPolygon, equalTo(JTSConverter.convertToJTS(geoPolygon)));
    }

    @Test
    void convertMultiPointToJTSTest() {
        MultiPoint geoMultiPoint = (MultiPoint) GeoJSONFactory.create("""
                {"type":"MultiPoint","coordinates":[[0.0,0.0],[1.0,1.0],[2.0,0.0]]}""");
        assertThat(jtsMultiPoint, equalTo(JTSConverter.convertToJTS(geoMultiPoint)));
    }

    @Test
    void convertMultiLineStringToJTSTest() {
        MultiLineString geoMultiLineString = (MultiLineString) GeoJSONFactory.create("""
                {"type":"MultiLineString","coordinates":[
                    [[0.0,0.0],[1.0,1.0],[2.0,0.0]],
                    [[0.0,0.0],[1.0,1.0],[2.0,0.0]]
                ]}""");
        assertThat(jtsMultiLineString, equalTo(JTSConverter.convertToJTS(geoMultiLineString)));
    }

    @Test
    void convertMultiPolygonToJTSTest() {
        MultiPolygon geoMultiPolygon = (MultiPolygon) GeoJSONFactory.create("""
                {"type":"MultiPolygon","coordinates":[
                    [[[0.0,0.0],[4.0,0.0],[4.0,4.0],[0.0,4.0],[0.0,0.0]]],
                    [[[0.0,0.0],[4.0,0.0],[4.0,4.0],[0.0,4.0],[0.0,0.0]]]
                ]}""");
        assertThat(jtsMultiPolygon, equalTo(JTSConverter.convertToJTS(geoMultiPolygon)));
    }
}
