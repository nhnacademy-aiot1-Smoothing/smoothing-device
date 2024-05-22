package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SensorTopicResponse {
    private List<SensorTopicDto> sensorWithTopics;
}
