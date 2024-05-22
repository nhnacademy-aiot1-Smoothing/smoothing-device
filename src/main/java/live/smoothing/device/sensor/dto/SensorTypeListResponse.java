package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 센서 타입 목록 응답 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
public class SensorTypeListResponse {

    @JsonProperty("sensorTypes")
    private List<SensorTypeResponse> sensorTypes;
}
