package live.smoothing.device.sensor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 센서 에러 목록 응답 클래스
 *
 * @author 우혜승
 */
@AllArgsConstructor
public class SensorErrorListResponse {
    @JsonProperty("errors")
    private final List<SensorErrorResponse> errors;
}
