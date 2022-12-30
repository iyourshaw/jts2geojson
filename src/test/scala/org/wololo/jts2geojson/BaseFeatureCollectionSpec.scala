package org.wololo.jts2geojson

import org.scalatest.WordSpec
import org.wololo.geojson._

import java.util.HashMap
import java.util.UUID

class BaseFeatureCollectionSpec extends WordSpec {

    "GeoJSONFactory" when {
      "parsing GeoJSON to ExamplePojoFeatureCollection" should {
            val id1 = UUID.fromString("f085e9c9-f1e9-41e7-92f9-a37979c7a23d");
            val geometry1 = new Point(Array(1, 1))
            val properties1 = new ExamplePojoProperties()
            properties1.setStr("test")
            properties1.setIdx(1)
            val feature1 = new ExamplePojoFeature(id1, geometry1, properties1)

            val id2 = UUID.fromString("f085e9c9-f1e9-41e7-0000-a37979c7a23d");
            val geometry2 = new Point(Array(2, 2))
            val properties2 = new ExamplePojoProperties()
            properties2.setStr("test2")
            properties2.setIdx(2)
            val feature2 = new ExamplePojoFeature(id2, geometry2, properties2)

            val featureColl = new ExamplePojoFeatureCollection(Array(feature1, feature2))
          "be identical to programmatically created ExamplePojoFeature" in {
              val expected = """{"type":"FeatureCollection","features":[{"type":"Feature","id":"f085e9c9-f1e9-41e7-92f9-a37979c7a23d","geometry":{"type":"Point","coordinates":[1.0,1.0]},"properties":{"str":"test","idx":1}},{"type":"Feature","id":"f085e9c9-f1e9-41e7-0000-a37979c7a23d","geometry":{"type":"Point","coordinates":[2.0,2.0]},"properties":{"str":"test2","idx":2}}]}"""
              val json = featureColl.toString
              assert(expected == json)
              val createdFeatureColl = GeoJSONFactory.createFeatureCollection(json, classOf[ExamplePojoFeatureCollection])
              assert(createdFeatureColl.getClass == classOf[ExamplePojoFeatureCollection])
              assert(expected == createdFeatureColl.toString)
          }
      }
      
     
    }    
}