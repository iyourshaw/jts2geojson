## Introduction

JTS to GeoJSON converter.  This repo (https://github.com/iyourshaw/jts2geojson) contains a customized version of the 
'jts2geojson' library originally created by Björn Harrtell (https://github.com/bjornharrtell/jts2geojson).

This fork adds the ability to allow the GeoJSON properties to be defined using a POJO class instead of a map, and to 
define custom feature classes to serialize and deserialize to/from GeoJSON.

This Java library can convert JTS geometries to GeoJSON and back. Its API is similar to other io.* classes in JTS.

## Usage

```java

GeoJSONWriter writer = new GeoJSONWriter();
GeoJSON json = writer.write(geometry);
String jsonstring = json.toString();

GeoJSONReader reader = new GeoJSONReader();
Geometry geometry = reader.read(json);
  
```

## Features and FeatureCollections

JTS does not have anything like GeoJSON Feature or FeatureCollection but they can be parsed by this library. Example:

```java

// parse Feature or FeatureCollection
Feature feature = (Feature) GeoJSONFactory.create(json);
FeatureCollection featureCollection = (FeatureCollection) GeoJSONFactory.create(json);

// parse Geometry from Feature
GeoJSONReader reader = new GeoJSONReader();
Geometry geometry = reader.read(feature.getGeometry());
geometry = reader.read(featureCollection.getFeatures()[0].getGeometry());

// create and serialize a FeatureCollection
List<Features> features = new ArrayList<Features>();
Map<String, Object> properties = new HashMap<String, Object>();
features.add(new Feature(geometry, properties);
GeoJSONWriter writer = new GeoJSONWriter();
GeoJSON json = writer.write(features);

```

## POJO Properties Serialization/Deserialization Examples

Define a regular POJO containing a couple properties with getters and setters:
```java
public class ExamplePojoProperties {
    
    private String str;
    private int idx;

    public String getStr() {
        return this.str;
    }
    public void setStr(String str) {
        this.str = str;
    }

   public int getIdx() {
        return this.idx;
    }
    public void setIdx(int idx) {
        this.idx = idx;
    }
}
```

Define an immutable POJO properties class with only getters and Jackson constructor annotations:
```java
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExampleImmutablePojoProperties {
    
    private final String str;
    private final int idx;

    @JsonCreator
    public ExampleImmutablePojoProperties(@JsonProperty("str") String str, @JsonProperty("idx") int idx) {
        this.str = str;
        this.idx = idx;
    }

    public String getStr() {
        return str;
    }
    public int getIdx() {
        return idx;
    }
}

```

Define a feature classes with Point geometry and using UUID's as ID for both POJOs:

```java
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamplePojoFeature extends BaseFeature<UUID, Point, ExamplePojoProperties> {

    @JsonCreator
    public ExamplePojoFeature(
            @JsonProperty("id") UUID id, 
            @JsonProperty("geometry") Point geometry, 
            @JsonProperty("properties") ExamplePojoProperties properties) {
        super(id, geometry, properties);
    }

}
```

```java
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExampleImmutablePojoFeature extends BaseFeature<UUID, Point, ExampleImmutablePojoProperties> {
    
        @JsonCreator
        public ExampleImmutablePojoFeature(
                @JsonProperty("id") UUID id, 
                @JsonProperty("geometry") Point geometry, 
                @JsonProperty("properties") ExampleImmutablePojoProperties properties) {
            super(id, geometry, properties);
        }
}

```

Create objects of the custom feature classes and serialize them to  GeoJSON:
```java
// Regular POJO
var id1 = UUID.randomUUID();
var geometry1 = new Point(-104.123, 45.4568); // Lon, Lat
var properties1 = new ExamplePojoProperties();
properties1.setStr("String property value 1");
properties1.setIdx(100);
var feature1 = new ExamplePojoFeature(id1, geometry1, properties1);
String json1 = feature1.toString();

// Immutable POJO
var id2 = UUID.randomUUID();
var geometry2 = new Point(-105.0, 50.0);
var properties2 = new ExampleImmutablePojoProperties("String property value 2", 200);
var feature2 = new ExampleImmutablePojoFeature(id2, geometry2, properties2);
String json2 = feature2.toString();
```

Deserialize some GeoJSON into instances of the custom feature classes:
```java
String json = "{'type':'Feature','id':'f085e9c9-f1e9-41e7-92f9-a37979c7a23d','geometry':{'type':'Point','coordinates':[1.0,1.0]},'properties':{'str':'test','idx':1}}";

// Deserialize to a regular POJO
ExamplePojoFeature feature = GeoJSONFactory.createFeature(json, ExamplePojoFeature.class);

// Deserialize to an immutable POJO
ExampleImmutableFeature immutableFeature = GeoJSONFactory.createFeature(json, ExampleImmutablePojoFeature.class);
```

Create a FeatureCollection class
```java
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExamplePojoFeatureCollection extends BaseFeatureCollection<ExamplePojoFeature> {

    @JsonCreator
    public ExamplePojoFeatureCollection(@JsonProperty("features") ExamplePojoFeature[] features) {
        super(features);
    }
}
```

Deserialize and serialize a FeatureCollection class
```java
String json = "{'type':'FeatureCollection','features':[{'type':'Feature','id':'f085e9c9-f1e9-41e7-92f9-a37979c7a23d','geometry':{'type':'Point','coordinates':[1.0,1.0]},'properties':{'str':'test','idx':1}},{'type':'Feature','id':'f085e9c9-f1e9-41e7-0000-a37979c7a23d','geometry':{'type':'Point','coordinates':[2.0,2.0]},'properties':{'str':'test2','idx':2}}]}"

// Deserialize
ExamplePojoFeatureCollection featureCollection = GeoJSONFactory.createFeatureCollection(json, ExamplePojoFeatureCollection.class);

// Serialize
String serializedJSON = featureCollection.toString();

```