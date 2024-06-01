package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class SensorTagAlreadyExistException extends CommonException {
    public SensorTagAlreadyExistException() {
        super(HttpStatus.BAD_REQUEST, "이미 등록된 태그입니다.");
    }
}
