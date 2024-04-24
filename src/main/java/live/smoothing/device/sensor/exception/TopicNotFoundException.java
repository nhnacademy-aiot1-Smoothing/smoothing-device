package live.smoothing.device.sensor.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class TopicNotFoundException extends CommonException {
    public TopicNotFoundException() {
        super(HttpStatus.NOT_FOUND,"토픽을 찾을 수 없습니다.");
    }
}
