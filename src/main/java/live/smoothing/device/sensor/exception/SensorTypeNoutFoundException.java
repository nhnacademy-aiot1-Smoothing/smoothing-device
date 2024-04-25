package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class SensorTypeNoutFoundException extends CommonException {
    public SensorTypeNoutFoundException() {
        super(HttpStatus.NOT_FOUND, "센서 타입을 찾을 수 없습니다.");
    }
}
