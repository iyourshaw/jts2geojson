package io.github.iyourshaw.geojson;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.github.iyourshaw.geojson.examples.ExampleJTSFeature;
import io.github.iyourshaw.geojson.examples.ExamplePojoProperties;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

public class BaseJTSFeatureTest {

  @Test
  public void getGeometryTest() {
    var factory = new GeometryFactory();
    var jtsPoint = factory.createPoint(new Coordinate(1.0, 2.0));
    var feature = new ExampleJTSFeature(jtsPoint, null);
    assertThat(feature.getGeometry(), notNullValue());
    assertThat(jtsPoint, equalTo(feature.getGeometry()));
  }

  @Test
  public void getPropertiesTest() {
    var factory = new GeometryFactory();
    var jtsPoint = factory.createPoint(new Coordinate(1.0, 2.0));
    var properties = new ExamplePojoProperties();
    properties.setStr("test");
    properties.setIdx(42);
    var feature = new ExampleJTSFeature(jtsPoint, properties);
    assertThat(feature.getProperties(), notNullValue());
    assertThat("test", equalTo(feature.getProperties().getStr()));
    assertThat(42, equalTo(feature.getProperties().getIdx()));
  }
}
