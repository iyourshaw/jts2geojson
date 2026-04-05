package com.iymaps.geojson;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * General-purpose GeoJSON properties backed by a LinkedHashMap.
 */
public class Properties extends LinkedHashMap<String, Object> {

    /** Creates an empty properties map. */
    public Properties() {
    }

    /**
     * Creates a properties map pre-populated from the given map.
     * @param propertyMap initial properties
     */
    public Properties(Map<String, Object> propertyMap) {
        this();
        putAll(propertyMap);
    }


}
