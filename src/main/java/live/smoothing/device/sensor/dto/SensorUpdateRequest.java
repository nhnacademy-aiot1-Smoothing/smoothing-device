package live.smoothing.device.sensor.dto;

import lombok.Getter;

/**
 * 센서 수정 요청 클래스
 *
 * @author 우혜승
 */
@Getter
public class SensorUpdateRequest {

    private String sensorType;
    private String sensorName;
}
