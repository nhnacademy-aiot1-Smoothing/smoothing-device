package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SensorTagsResponse {
    Map<Integer, List<TagResponse>> sensorTags;
}
