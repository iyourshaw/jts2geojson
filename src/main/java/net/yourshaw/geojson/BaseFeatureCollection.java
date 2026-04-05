package net.yourshaw.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"type", "features"})
public abstract class BaseFeatureCollection<TFeature extends BaseFeature<?, ?, ?>> extends GeoJSON {

    @Override
    public String getGeoJSONType() {
        return "FeatureCollection";
    }

    private final TFeature[] features;
    
    @JsonCreator
    public BaseFeatureCollection(@JsonProperty("features") TFeature[] features) {
        super();
        this.features = features;
    }

}
