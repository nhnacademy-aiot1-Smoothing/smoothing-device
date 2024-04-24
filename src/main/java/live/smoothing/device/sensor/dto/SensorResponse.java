package live.smoothing.device.sensor.dto;

import live.smoothing.device.sensor.entity.SensorType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SensorResponse {

    private final Integer sensorId;
    private final String sensorType;
    private final String sensorName;
    private final LocalDateTime sensorRegisteredAt;

    public SensorResponse(Integer sensorId, SensorType sensorType, String sensorName, LocalDateTime sensorRegisteredAt) {
        this.sensorId = sensorId;
        this.sensorType = sensorType.getSensorType();
        this.sensorName = sensorName;
        this.sensorRegisteredAt = sensorRegisteredAt;
    }
}
