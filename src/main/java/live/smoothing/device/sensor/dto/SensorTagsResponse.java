package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SensorTagsResponse {
    @JsonProperty("sensorTags")
    Map<Integer, List<TagResponse>> sensorTags;
}
