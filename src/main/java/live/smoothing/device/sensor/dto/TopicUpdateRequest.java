package live.smoothing.device.sensor.dto;

import lombok.Getter;

/**
 * 토픽 수정 요청 클래스
 *
 * @author 우혜승
 */
@Getter
public class TopicUpdateRequest {
    private String topic;
    private String topicType;
}
