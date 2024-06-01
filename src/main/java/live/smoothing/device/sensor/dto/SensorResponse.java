package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import live.smoothing.device.sensor.entity.SensorType;

import java.time.LocalDateTime;

/**
 * 센서 응답 클래스
 *
 * @author 우혜승
 */
public class SensorResponse {

    @JsonProperty("sensorId")
    private final Integer sensorId;
    @JsonProperty("sensorType")
    private final String sensorType;
    @JsonProperty("sensorName")
    private final String sensorName;
    @JsonProperty("sensorRegisteredAt")
    private final String sensorRegisteredAt;

    public SensorResponse(Integer sensorId, SensorType sensorType, String sensorName, LocalDateTime sensorRegisteredAt) {
        this.sensorId = sensorId;
        this.sensorType = sensorType.getSensorType();
        this.sensorName = sensorName;
        this.sensorRegisteredAt = sensorRegisteredAt == null ? null : sensorRegisteredAt.toString();
    }
}
