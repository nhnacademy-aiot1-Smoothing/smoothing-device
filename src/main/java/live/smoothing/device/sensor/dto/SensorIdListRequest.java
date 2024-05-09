package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SensorIdListRequest {
    private List<Integer> sensorIds;
}
