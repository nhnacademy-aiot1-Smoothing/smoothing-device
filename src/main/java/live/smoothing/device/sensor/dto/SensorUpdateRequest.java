package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorUpdateRequest {

    private String sensorType;
    private String sensorName;
}
