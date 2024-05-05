package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class TagNotFoundException extends CommonException {
    public TagNotFoundException() {
        super(HttpStatus.NOT_FOUND, "태그를 찾을 수 없습니다.");
    }
}
