package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토픽 추가 요청 클래스
 *
 * @author 우혜승
 */
@Getter
@NoArgsConstructor
public class TopicAddRequest {

    private String topic;
    private String topicType;
    private Integer sensorId;
}
