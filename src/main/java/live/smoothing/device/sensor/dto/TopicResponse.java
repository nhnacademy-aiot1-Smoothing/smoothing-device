package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 토픽 응답 클래스
 *
 * @author 우혜승
 */
@Getter
@AllArgsConstructor
public class TopicResponse {

    private final Integer topicId;
    private final String topicType;
    private final String topic;
    private final String sensorName;
}
