package io.github.iyourshaw.jts2geojson;

import io.github.iyourshaw.geojson.Feature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeoJSONReaderTest {

    public static GeoJSONReader reader;
    public static GeoJSONWriter writer;
    public static GeometryFactory factory;
    public static int srid;
    public static GeometryFactory factorySrid;
    public static Coordinate[] coordArray;

    @BeforeAll
    public static void setup() {
        reader = new GeoJSONReader();
        writer = new GeoJSONWriter();
        factory = new GeometryFactory();
        srid = 25832;
        factorySrid = new GeometryFactory(new PrecisionModel(), srid);
        coordArray = new Coordinate[] {
                new Coordinate(1, 1),
                new Coordinate(1, 2),
                new Coordinate(2, 2),
                new Coordinate(1, 1)
        };
    }

    @Test
    public void readPointTest() {
        Point expected = factory.createPoint(new Coordinate(1, 1));
        Point expectedSrid = factorySrid.createPoint(new Coordinate(1, 1));
        String json = """
                {"type":"Point","coordinates":[1.0,1.0]}""";

        Geometry geometry = reader.read(json);
        assertThat(expected, equalTo(geometry));
        assertThat(0, equalTo(geometry.getSRID()));

        geometry = reader.read(json, factorySrid);
        assertThat(expectedSrid, equalTo(geometry));
        assertThat(srid, equalTo(geometry.getSRID()));
    }

    @Test
    public void readMultiPointTest() {
        MultiPoint expected = factory.createMultiPointFromCoords(coordArray);
        MultiPoint expectedSrid = factorySrid.createMultiPointFromCoords(coordArray);
        String json = """
                {"type":"MultiPoint","coordinates":[[1.0,1.0],[1.0,2.0],[2.0,2.0],[1.0,1.0]]}""";

        Geometry geometry = reader.read(json);
        assertThat(expected, equalTo(geometry));
        assertThat(0, equalTo(geometry.getSRID()));

        geometry = reader.read(json, factorySrid);
        assertThat(expectedSrid, equalTo(geometry));
        assertThat(srid, equalTo(geometry.getSRID()));
    }

    @Test
    public void readPolygonTest() {
        var shellCoords = new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(4, 0),
                new Coordinate(4, 4), new Coordinate(0, 4),
                new Coordinate(0, 0)
        };
        Polygon expected = factory.createPolygon(shellCoords);
        Polygon expectedSrid = factorySrid.createPolygon(shellCoords);
        String json = """
                {"type":"Polygon","coordinates":[
                    [[0.0,0.0],[4.0,0.0],[4.0,4.0],[0.0,4.0],[0.0,0.0]]
                ]}""";

        Geometry geometry = reader.read(json);
        assertThat(expected, equalTo(geometry));
        assertThat(0, equalTo(geometry.getSRID()));

        geometry = reader.read(json, factorySrid);
        assertThat(expectedSrid, equalTo(geometry));
        assertThat(srid, equalTo(geometry.getSRID()));
    }

    @Test
    public void readPolygonWithHolesTest() {
        var shellCoords = new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(10, 0),
                new Coordinate(10, 10), new Coordinate(0, 10),
                new Coordinate(0, 0)
        };
        var hole1Coords = new Coordinate[]{
                new Coordinate(1, 1), new Coordinate(3, 1),
                new Coordinate(3, 3), new Coordinate(1, 3),
                new Coordinate(1, 1)
        };
        var hole2Coords = new Coordinate[]{
                new Coordinate(5, 5), new Coordinate(7, 5),
                new Coordinate(7, 7), new Coordinate(5, 7),
                new Coordinate(5, 5)
        };
        Polygon expected = factory.createPolygon(
                factory.createLinearRing(shellCoords),
                new org.locationtech.jts.geom.LinearRing[]{
                        factory.createLinearRing(hole1Coords),
                        factory.createLinearRing(hole2Coords)
                });
        Polygon expectedSrid = factorySrid.createPolygon(
                factorySrid.createLinearRing(shellCoords),
                new org.locationtech.jts.geom.LinearRing[]{
                        factorySrid.createLinearRing(hole1Coords),
                        factorySrid.createLinearRing(hole2Coords)
                });
        String json = """
                {"type":"Polygon","coordinates":[
                    [[0.0,0.0],[10.0,0.0],[10.0,10.0],[0.0,10.0],[0.0,0.0]],
                    [[1.0,1.0],[3.0,1.0],[3.0,3.0],[1.0,3.0],[1.0,1.0]],
                    [[5.0,5.0],[7.0,5.0],[7.0,7.0],[5.0,7.0],[5.0,5.0]]
                ]}""";

        Geometry geometry = reader.read(json);
        assertThat(expected, equalTo(geometry));
        assertThat(0, equalTo(geometry.getSRID()));

        geometry = reader.read(json, factorySrid);
        assertThat(expectedSrid, equalTo(geometry));
        assertThat(srid, equalTo(geometry.getSRID()));
    }

    @Test
    public void readGeometryCollectionTest() {
        var pointCoord = new Coordinate(1, 2);
        var lineCoords = new Coordinate[]{new Coordinate(0, 0), new Coordinate(3, 4)};
        var polygonCoords = new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(5, 0),
                new Coordinate(5, 5), new Coordinate(0, 5),
                new Coordinate(0, 0)
        };
        GeometryCollection expected = factory.createGeometryCollection(new Geometry[]{
                factory.createPoint(pointCoord),
                factory.createLineString(lineCoords),
                factory.createPolygon(polygonCoords)
        });
        GeometryCollection expectedSrid = factorySrid.createGeometryCollection(new Geometry[]{
                factorySrid.createPoint(pointCoord),
                factorySrid.createLineString(lineCoords),
                factorySrid.createPolygon(polygonCoords)
        });
        String json = """
                {"type":"GeometryCollection","geometries":[
                    {"type":"Point","coordinates":[1.0,2.0]},
                    {"type":"LineString","coordinates":[[0.0,0.0],[3.0,4.0]]},
                    {"type":"Polygon","coordinates":[[[0.0,0.0],[5.0,0.0],[5.0,5.0],[0.0,5.0],[0.0,0.0]]]}
                ]}""";

        Geometry geometry = reader.read(json);
        assertThat(expected, equalTo(geometry));
        assertThat(0, equalTo(geometry.getSRID()));

        geometry = reader.read(json, factorySrid);
        assertThat(expectedSrid, equalTo(geometry));
        assertThat(srid, equalTo(geometry.getSRID()));
    }

    @Test
    public void readMultiPolygonTest() {
        var poly1Coords = new Coordinate[]{
                new Coordinate(0, 0), new Coordinate(1, 0),
                new Coordinate(1, 1), new Coordinate(0, 1),
                new Coordinate(0, 0)
        };
        var poly2Coords = new Coordinate[]{
                new Coordinate(2, 2), new Coordinate(3, 2),
                new Coordinate(3, 3), new Coordinate(2, 3),
                new Coordinate(2, 2)
        };
        MultiPolygon expected = factory.createMultiPolygon(new Polygon[]{
                factory.createPolygon(poly1Coords),
                factory.createPolygon(poly2Coords)
        });
        MultiPolygon expectedSrid = factorySrid.createMultiPolygon(new Polygon[]{
                factorySrid.createPolygon(poly1Coords),
                factorySrid.createPolygon(poly2Coords)
        });
        String json = """
                {"type":"MultiPolygon","coordinates":[
                    [[[0.0,0.0],[1.0,0.0],[1.0,1.0],[0.0,1.0],[0.0,0.0]]],
                    [[[2.0,2.0],[3.0,2.0],[3.0,3.0],[2.0,3.0],[2.0,2.0]]]
                ]}""";

        Geometry geometry = reader.read(json);
        assertThat(expected, equalTo(geometry));
        assertThat(0, equalTo(geometry.getSRID()));

        geometry = reader.read(json, factorySrid);
        assertThat(expectedSrid, equalTo(geometry));
        assertThat(srid, equalTo(geometry.getSRID()));
    }

    @Test
    public void readUnsupportedTypeThrowsTest() {
        var feature = new Feature(null, null, null);
        assertThrows(UnsupportedOperationException.class, () -> reader.read(feature));
    }

    @Test
    public void readMultiLineStringTest() {
        var line1Coords = new Coordinate[]{new Coordinate(1, 1), new Coordinate(2, 2)};
        var line2Coords = new Coordinate[]{new Coordinate(3, 3), new Coordinate(4, 4)};
        MultiLineString expected = factory.createMultiLineString(new LineString[]{
                factory.createLineString(line1Coords),
                factory.createLineString(line2Coords)
        });
        MultiLineString expectedSrid = factorySrid.createMultiLineString(new LineString[]{
                factorySrid.createLineString(line1Coords),
                factorySrid.createLineString(line2Coords)
        });
        String json = """
                {"type":"MultiLineString","coordinates":[
                    [[1.0,1.0],[2.0,2.0]],
                    [[3.0,3.0],[4.0,4.0]]
                ]}""";

        Geometry geometry = reader.read(json);
        assertThat(expected, equalTo(geometry));
        assertThat(0, equalTo(geometry.getSRID()));

        geometry = reader.read(json, factorySrid);
        assertThat(expectedSrid, equalTo(geometry));
        assertThat(srid, equalTo(geometry.getSRID()));
    }


}
