package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class SensorNotFoundException extends CommonException {
    public SensorNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 센서입니다.");
    }
}
