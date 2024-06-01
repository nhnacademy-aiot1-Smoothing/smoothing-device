package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class TagOwnerException extends CommonException {
    public TagOwnerException() {
        super(HttpStatus.BAD_REQUEST, "태그 소유자가 아닙니다.");
    }
}
