package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Rule Engine 토픽 요청 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
public class TopicRequest {
    private Integer brokerId;
    private String topic;
}
