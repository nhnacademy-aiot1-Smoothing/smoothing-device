package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SensorErrorResponse {

    private final String sensorErrorType;
    private final String sensorName;
    private final LocalDateTime createdAt;
    private final Double value;
    private final String topic;
}
