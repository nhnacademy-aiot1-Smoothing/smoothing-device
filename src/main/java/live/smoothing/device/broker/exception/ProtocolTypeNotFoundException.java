package live.smoothing.device.broker.exception;

import live.smoothing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class ProtocolTypeNotFoundException extends CommonException {
    public ProtocolTypeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 프로토콜 타입입니다.");
    }
}
