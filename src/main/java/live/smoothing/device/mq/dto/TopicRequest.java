package live.smoothing.device.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicRequest {
    private Integer brokerId;
    private String topic;
}
