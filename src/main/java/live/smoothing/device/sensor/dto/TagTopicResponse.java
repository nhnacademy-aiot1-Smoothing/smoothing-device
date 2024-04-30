package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class TagTopicResponse {
    private final List<String> topics;
}
