package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 센서 타입 목록 응답 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class SensorTypeListResponse {

    private List<SensorTypeResponse> sensorTypes;
}
