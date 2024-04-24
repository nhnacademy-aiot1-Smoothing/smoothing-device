package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TopicUpdateRequest {
    private String topic;
    private String topicType;
}
