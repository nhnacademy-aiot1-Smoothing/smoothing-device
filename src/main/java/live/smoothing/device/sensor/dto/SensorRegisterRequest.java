package live.smoothing.device.sensor.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 센서 등록 요청 클래스
 *
 * @author 우혜승
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorRegisterRequest {

    private String sensorType;
    private String sensorName;
    private Integer brokerId;
}
