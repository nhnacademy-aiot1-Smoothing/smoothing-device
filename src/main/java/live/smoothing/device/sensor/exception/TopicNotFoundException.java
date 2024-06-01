package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 토픽을 찾을 수 없을 때 발생하는 예외
 *
 * @author 우혜승
 */
public class TopicNotFoundException extends CommonException {
    public TopicNotFoundException() {
        super(HttpStatus.NOT_FOUND,"토픽을 찾을 수 없습니다.");
    }
}
