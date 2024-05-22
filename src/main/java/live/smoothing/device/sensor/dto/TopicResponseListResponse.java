package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 토픽 목록 응답 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
public class TopicResponseListResponse {
    @JsonProperty("topics")
    private List<TopicResponse> topics;
    @JsonProperty("totalPage")
    private int totalPage;
}
