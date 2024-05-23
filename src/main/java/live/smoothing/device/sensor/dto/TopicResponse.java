package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

/**
 * 토픽 응답 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
public class TopicResponse {

    @JsonProperty("topicId")
    private final Integer topicId;
    @JsonProperty("topicType")
    private final String topicType;
    @JsonProperty("topic")
    private final String topic;
    @JsonProperty("sensorName")
    private final String sensorName;
}
