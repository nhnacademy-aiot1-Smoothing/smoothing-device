package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class TopicAlreadyExistException extends CommonException {
    public TopicAlreadyExistException() {
        super(HttpStatus.BAD_REQUEST, "이미 존재하는 토픽입니다.");
    }
}
