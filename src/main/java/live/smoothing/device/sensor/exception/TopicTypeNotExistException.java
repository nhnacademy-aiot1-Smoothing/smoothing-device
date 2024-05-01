package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 토픽 타입이 존재하지 않을 때 발생하는 예외
 *
 * @author 우혜승
 */
public class TopicTypeNotExistException extends CommonException {
    public TopicTypeNotExistException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 토픽 타입입니다.");
    }
}
