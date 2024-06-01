package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class TagAlreadyExistException extends CommonException {
    public TagAlreadyExistException() {
        super(HttpStatus.BAD_REQUEST, "태그가 이미 존재합니다.");
    }
}
