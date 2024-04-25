package live.smoothing.device.mq.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SensorErrorRequest {

    private String sensorErrorType;
    private LocalDateTime createdAt;
    private Double sensorValue;
    private Integer sensorId;
    private String topic;
}
