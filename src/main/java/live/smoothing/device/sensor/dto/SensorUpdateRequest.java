package live.smoothing.device.sensor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 센서 수정 요청 클래스
 *
 * @author 우혜승
 */
@Getter
@NoArgsConstructor
public class SensorUpdateRequest {

    private String sensorType;
    private String sensorName;
}
