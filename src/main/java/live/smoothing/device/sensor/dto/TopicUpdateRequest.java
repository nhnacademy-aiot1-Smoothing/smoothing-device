package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토픽 수정 요청 클래스
 *
 * @author 우혜승
 */
@Getter
@NoArgsConstructor
public class TopicUpdateRequest {
    private String topic;
    private String topicType;
}
