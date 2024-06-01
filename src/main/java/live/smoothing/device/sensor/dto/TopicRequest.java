package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Rule Engine 토픽 요청 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
public class TopicRequest {
    @JsonProperty("brokerId")
    private Integer brokerId;
    @JsonProperty("topic")
    private String topic;
}
