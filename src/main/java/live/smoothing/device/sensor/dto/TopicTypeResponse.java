package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

/**
 * 토픽 타입 응답 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
public class TopicTypeResponse {

    @JsonProperty("topicType")
    private final String topicType;
}
