package org.wololo.jts2geojson

import org.scalatest.WordSpec
import org.wololo.geojson._

import java.util.HashMap
import java.util.UUID

class BaseFeatureSpec extends WordSpec {

    "GeoJSONFactory" when {
      "parsing GeoJSON to ExamplePojoFeature" should {
            val id = UUID.fromString("f085e9c9-f1e9-41e7-92f9-a37979c7a23d");
            val geometry = new Point(Array(1, 1))
            val properties = new ExamplePojoProperties()
            properties.setStr("test")
            properties.setIdx(1)
            val feature = new ExamplePojoFeature(id, geometry, properties)
          "be identical to programmatically created ExamplePojoFeature" in {
              val expected = """{"type":"Feature","id":"f085e9c9-f1e9-41e7-92f9-a37979c7a23d","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"str":"test","idx":1}}"""
              val json = feature.toString
              assert(expected == json)
              val createdFeature = GeoJSONFactory.createFeature(json, classOf[ExamplePojoFeature])
              assert(createdFeature.getClass == classOf[ExamplePojoFeature])
              assert(expected == createdFeature.toString)
          }
      }
      "parsing GeoJSON to ExampleImmutablePojoFeature" should {
        val id = UUID.fromString("f085e9c9-f1e9-41e7-92f9-a37979c7a23d");
        val geometry = new Point(Array(1, 1))
        val properties = new ExampleImmutablePojoProperties("test", 1)
        var feature = new ExampleImmutablePojoFeature(id, geometry, properties)
        "be identical to programmatically created ExampleImmutablePojoFeature" in {
              val expected = """{"type":"Feature","id":"f085e9c9-f1e9-41e7-92f9-a37979c7a23d","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"str":"test","idx":1}}"""
              val json = feature.toString
              assert(expected == json)
              val createdFeature = GeoJSONFactory.createFeature(json, classOf[ExampleImmutablePojoFeature])
              assert(createdFeature.getClass == classOf[ExampleImmutablePojoFeature])
              assert(expected == createdFeature.toString)
        }
      }
     
    }    
}