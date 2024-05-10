package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorTagAddRequest {
    private Integer sensorId;
    private Integer tagId;
}
