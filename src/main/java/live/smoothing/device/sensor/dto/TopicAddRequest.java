package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TopicAddRequest {

    private String topic;
    private String topicType;
    private Integer sensorId;
}
