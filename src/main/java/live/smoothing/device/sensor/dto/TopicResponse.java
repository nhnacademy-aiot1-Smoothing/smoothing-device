package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicResponse {

    private final Integer topicId;
    private final String topicType;
    private final String topic;
    private final String sensorName;
}
