package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class TopicTypeNotExistException extends CommonException {
    public TopicTypeNotExistException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 토픽 타입입니다.");
    }
}
