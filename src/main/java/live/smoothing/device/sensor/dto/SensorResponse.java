package live.smoothing.device.sensor.dto;

import live.smoothing.device.sensor.entity.SensorType;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 센서 응답 클래스
 *
 * @author 우혜승
 */
@Getter
public class SensorResponse {

    private final Integer sensorId;
    private final String sensorType;
    private final String sensorName;
    private final String sensorRegisteredAt;

    public SensorResponse(Integer sensorId, SensorType sensorType, String sensorName, LocalDateTime sensorRegisteredAt) {
        this.sensorId = sensorId;
        this.sensorType = sensorType.getSensorType();
        this.sensorName = sensorName;
        this.sensorRegisteredAt = sensorRegisteredAt == null ? null : sensorRegisteredAt.toString();
    }
}
