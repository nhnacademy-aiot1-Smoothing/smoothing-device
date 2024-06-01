package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 토픽이 이미 존재할 때 발생하는 예외
 *
 * @author 우혜승
 */
public class TopicAlreadyExistException extends CommonException {
    public TopicAlreadyExistException() {
        super(HttpStatus.BAD_REQUEST, "이미 존재하는 토픽입니다.");
    }
}
