package live.smoothing.device.sensor.dto;

import live.smoothing.device.sensor.entity.SensorType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorRegisterRequest {

    private String sensorType;
    private String sensorName;
    private Integer brokerId;
}
