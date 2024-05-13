package live.smoothing.device.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 센서 목록 응답 클래스
 *
 * @author 우혜승
 */
@Getter
@AllArgsConstructor
public class SensorListResponse {

    private final List<SensorResponse> sensors;

    private final int totalPage;
}
