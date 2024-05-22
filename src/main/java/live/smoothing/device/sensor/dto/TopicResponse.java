package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 토픽 응답 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class TopicResponse {

    private final Integer topicId;
    private final String topicType;
    private final String topic;
    private final String sensorName;
}
