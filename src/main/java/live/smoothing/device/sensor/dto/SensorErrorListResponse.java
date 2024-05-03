package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 센서 에러 목록 응답 클래스
 *
 * @author 우혜승
 */
@Getter
@AllArgsConstructor
public class SensorErrorListResponse {
    private final List<SensorErrorResponse> errors;
}
