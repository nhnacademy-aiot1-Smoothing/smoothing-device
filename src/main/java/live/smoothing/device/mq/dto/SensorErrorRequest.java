package live.smoothing.device.mq.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 센서 에러 추가 요청 클래스
 *
 * @author 우혜승
 */
@Getter
@NoArgsConstructor
public class SensorErrorRequest {

    private String sensorErrorType;
    private LocalDateTime createdAt;
    private Double sensorValue;
    private Integer sensorId;
    private String topic;
}
