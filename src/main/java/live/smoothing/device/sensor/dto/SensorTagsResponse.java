package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SensorTagsResponse {
    Map<Integer, List<TagResponse>> sensorTags;
}
