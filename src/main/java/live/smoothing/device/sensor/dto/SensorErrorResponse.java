package live.smoothing.device.sensor.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 센서 에러 응답 클래스
 *
 * @author 우혜승
 */
@Getter
public class SensorErrorResponse {

    private final String sensorErrorType;
    private final String sensorName;
    private final String createdAt;
    private final Double value;
    private final String topic;

    public SensorErrorResponse(String sensorErrorType, String sensorName, LocalDateTime createdAt, Double value, String topic) {
        this.sensorErrorType = sensorErrorType;
        this.sensorName = sensorName;
        this.createdAt = createdAt == null ? null : createdAt.toString();
        this.value = value;
        this.topic = topic;
    }
}
