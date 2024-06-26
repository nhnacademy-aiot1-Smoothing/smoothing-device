package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * 센서 에러 응답 클래스
 *
 * @author 우혜승
 */
public class SensorErrorResponse {

    @JsonProperty("sensorErrorId")
    private final Integer sensorErrorId;
    @JsonProperty("sensorErrorType")
    private final String sensorErrorType;
    @JsonProperty("brokerName")
    private final String brokerName;
    @JsonProperty("createdAt")
    private final String createdAt;
    @JsonProperty("value")
    private final Double value;
    @JsonProperty("topic")
    private final String topic;

    public SensorErrorResponse(Integer sensorErrorId ,String sensorErrorType, String brokerName, LocalDateTime createdAt, Double value, String topic) {
        this.sensorErrorId = sensorErrorId;
        this.sensorErrorType = sensorErrorType;
        this.brokerName = brokerName;
        this.createdAt = createdAt == null ? null : createdAt.toString();
        this.value = value;
        this.topic = topic;
    }
}
