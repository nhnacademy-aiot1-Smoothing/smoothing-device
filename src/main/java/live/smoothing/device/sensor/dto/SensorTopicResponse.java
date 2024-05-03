package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SensorTopicResponse {
    private List<SensorTopicDto> sensorWithTopics;
}
