package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 센서 타입을 찾을 수 없을 때 발생하는 예외
 *
 * @author 우혜승
 */
public class SensorTypeNotFoundException extends CommonException {
    public SensorTypeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "센서 타입을 찾을 수 없습니다.");
    }
}
