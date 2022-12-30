package org.wololo.jts2geojson

import java.util.HashMap

import org.scalatest.WordSpec
import org.wololo.geojson._

class DeserializeGeometryCollectionSpec extends WordSpec {
    "GeoJSONFactory" when {
      "parsing GeoJSON to object" should {
        val geometry = new Point(Array(1, 1))
        val map = new HashMap[String, Object]();
        map.put("test", 1.asInstanceOf[Object])
        val properties = new Properties(map);
        val feature = new Feature(geometry, properties)


        "deserialize GeometryCollection successfully" in {
          val geoJSON = """{"type": "GeometryCollection", "geometries": [{"type": "Point",  "coordinates": [1.1, 2.2] }]}"""
          val json = GeoJSONFactory.create(geoJSON)
          assert(json.isInstanceOf[GeometryCollection])
          val gc = json.asInstanceOf[GeometryCollection]
          assert(gc.getGeometries.nonEmpty)
          val g = gc.getGeometries.head
          assert(g != null)
          assert(g.isInstanceOf[Point])
          val point = g.asInstanceOf[Point]
          assert(point.getCoordinates.toSeq == Seq(1.1, 2.2))
        }

      }

    }
}
