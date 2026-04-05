package com.iymaps.geojson;

import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tools.jackson.databind.JavaType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class GeoJSONFactory {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Parses a GeoJSON string into the appropriate {@link GeoJSON} subtype.
     * @param json the GeoJSON string
     * @return a {@link Geometry}, {@link Feature}, or {@link FeatureCollection}
     */
    public static GeoJSON create(String json) {
        JsonNode node = mapper.readTree(json);
        String type = node.get("type").asString();
        if (type.equals("FeatureCollection")) {
            return readFeatureCollection(node);
        } else if (type.equals("Feature")) {
            return readFeature(node);
        } else {
            return readGeometry(node, type);
        }
    }

    /**
     * Deserialize JSON into a feature with POJO properties of a specific type
     * @param <TFeature> The Feature class that inherits from BaseFeature
     * @param json The JSON to deserialize
     * @param featureType The type of the feature class
     * @return an instance of the feature class
     */
    public static <TFeature extends BaseFeature<?, ?,?>> TFeature createFeature(String json, Class<TFeature> featureType) {
        return mapper.readValue(json, featureType);
    }

    /**
     * Deserializes a GeoJSON string into a typed {@link BaseFeatureCollection} subclass.
     * @param <TFeatureCollection> the feature collection type
     * @param json the GeoJSON string
     * @param featureCollectionType the target class
     * @return an instance of {@code TFeatureCollection}
     */
    public static <TFeatureCollection extends BaseFeatureCollection<?>> TFeatureCollection createFeatureCollection(
            String json, Class<TFeatureCollection> featureCollectionType) {
        return mapper.readValue(json, featureCollectionType);
    }
    
    private static FeatureCollection readFeatureCollection(JsonNode node) {
        Iterator<JsonNode> it = node.get("features").iterator();
        List<Feature> features = new ArrayList<>();
        while (it.hasNext()) {
            JsonNode jFeature = it.next();
            features.add(readFeature(jFeature));
        }
        
        return new FeatureCollection(features.toArray(new Feature[0]));
    }
    
    private static Feature readFeature(JsonNode node) {
        JsonNode geometryNode = node.get("geometry");
        JavaType javaType = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        Object id = node.get("id");
        JsonNode propertiesNode = node.get("properties");
        Map<String, Object> propertyMap = mapper.convertValue(propertiesNode, javaType);
        Properties properties = new Properties(propertyMap);
        Geometry geometry = readGeometry(geometryNode);
        return new Feature(id, geometry, properties);
    }

     
    private static Geometry readGeometry(JsonNode node) {
        if (!node.isNull()) {
            final String type = node.get("type").asString();
            return readGeometry(node, type);
        } else {
            return null;
        }
    }

    private static Geometry readGeometry(JsonNode node, String type) {
        final String packageName = Geometry.class.getPackageName();
        try {
            return (Geometry) mapper.convertValue(node,
                    Class.forName(packageName + "." + type));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
