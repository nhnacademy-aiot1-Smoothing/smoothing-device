package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 토픽 타입 목록 응답 클래스
 *
 * @author 우혜승
 */
@Getter
@AllArgsConstructor
public class TopicTypeListResponse {

    private final List<TopicTypeResponse> topicTypes;
}
