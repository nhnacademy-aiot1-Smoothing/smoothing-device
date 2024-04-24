package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TopicTypeListResponse {

    private final List<TopicTypeResponse> topicTypes;
}
