package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 센서를 찾을 수 없을 때 발생하는 예외
 *
 * @author 우혜승
 */
public class SensorNotFoundException extends CommonException {
    public SensorNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 센서입니다.");
    }
}
